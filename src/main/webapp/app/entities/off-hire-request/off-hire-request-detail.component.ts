import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { OffHireRequest } from './off-hire-request.model';
import { OffHireRequestService } from './off-hire-request.service';

@Component({
    selector: 'jhi-off-hire-request-detail',
    templateUrl: './off-hire-request-detail.component.html'
})
export class OffHireRequestDetailComponent implements OnInit, OnDestroy {

    offHireRequest: OffHireRequest;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private offHireRequestService: OffHireRequestService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOffHireRequests();
    }

    load(id) {
        this.offHireRequestService.find(id)
            .subscribe((offHireRequestResponse: HttpResponse<OffHireRequest>) => {
                this.offHireRequest = offHireRequestResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOffHireRequests() {
        this.eventSubscriber = this.eventManager.subscribe(
            'offHireRequestListModification',
            (response) => this.load(this.offHireRequest.id)
        );
    }
}
