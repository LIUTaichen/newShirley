import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EmailSubscription } from './email-subscription.model';
import { EmailSubscriptionService } from './email-subscription.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-email-subscription',
    templateUrl: './email-subscription.component.html'
})
export class EmailSubscriptionComponent implements OnInit, OnDestroy {
emailSubscriptions: EmailSubscription[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private emailSubscriptionService: EmailSubscriptionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.emailSubscriptionService.query().subscribe(
            (res: HttpResponse<EmailSubscription[]>) => {
                this.emailSubscriptions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEmailSubscriptions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EmailSubscription) {
        return item.id;
    }
    registerChangeInEmailSubscriptions() {
        this.eventSubscriber = this.eventManager.subscribe('emailSubscriptionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
