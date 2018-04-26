import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OffHireRequest } from './off-hire-request.model';
import { OffHireRequestService } from './off-hire-request.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-off-hire-request',
    templateUrl: './off-hire-request.component.html'
})
export class OffHireRequestComponent implements OnInit, OnDestroy {
offHireRequests: OffHireRequest[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private offHireRequestService: OffHireRequestService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.offHireRequestService.query().subscribe(
            (res: HttpResponse<OffHireRequest[]>) => {
                this.offHireRequests = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInOffHireRequests();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: OffHireRequest) {
        return item.id;
    }
    registerChangeInOffHireRequests() {
        this.eventSubscriber = this.eventManager.subscribe('offHireRequestListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
