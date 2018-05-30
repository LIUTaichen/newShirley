import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Niggle } from './niggle.model';
import { NiggleService } from './niggle.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-niggle',
    templateUrl: './niggle.component.html'
})
export class NiggleComponent implements OnInit, OnDestroy {
niggles: Niggle[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private niggleService: NiggleService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.niggleService.query().subscribe(
            (res: HttpResponse<Niggle[]>) => {
                this.niggles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInNiggles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Niggle) {
        return item.id;
    }
    registerChangeInNiggles() {
        this.eventSubscriber = this.eventManager.subscribe('niggleListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
