import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { EmailSubscription } from './email-subscription.model';
import { EmailSubscriptionService } from './email-subscription.service';

@Component({
    selector: 'jhi-email-subscription-detail',
    templateUrl: './email-subscription-detail.component.html'
})
export class EmailSubscriptionDetailComponent implements OnInit, OnDestroy {

    emailSubscription: EmailSubscription;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private emailSubscriptionService: EmailSubscriptionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmailSubscriptions();
    }

    load(id) {
        this.emailSubscriptionService.find(id)
            .subscribe((emailSubscriptionResponse: HttpResponse<EmailSubscription>) => {
                this.emailSubscription = emailSubscriptionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmailSubscriptions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'emailSubscriptionListModification',
            (response) => this.load(this.emailSubscription.id)
        );
    }
}
