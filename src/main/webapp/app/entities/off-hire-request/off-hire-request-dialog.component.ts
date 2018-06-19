import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OffHireRequest } from './off-hire-request.model';
import { OffHireRequestPopupService } from './off-hire-request-popup.service';
import { OffHireRequestService } from './off-hire-request.service';

@Component({
    selector: 'jhi-off-hire-request-dialog',
    templateUrl: './off-hire-request-dialog.component.html'
})
export class OffHireRequestDialogComponent implements OnInit {

    offHireRequest: OffHireRequest;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private offHireRequestService: OffHireRequestService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.offHireRequest.id !== undefined) {
            this.subscribeToSaveResponse(
                this.offHireRequestService.update(this.offHireRequest));
        } else {
            this.subscribeToSaveResponse(
                this.offHireRequestService.create(this.offHireRequest));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<OffHireRequest>>) {
        result.subscribe((res: HttpResponse<OffHireRequest>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: OffHireRequest) {
        this.eventManager.broadcast({ name: 'offHireRequestListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-off-hire-request-popup',
    template: ''
})
export class OffHireRequestPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private offHireRequestPopupService: OffHireRequestPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.offHireRequestPopupService
                    .open(OffHireRequestDialogComponent as Component, params['id']);
            } else {
                this.offHireRequestPopupService
                    .open(OffHireRequestDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
