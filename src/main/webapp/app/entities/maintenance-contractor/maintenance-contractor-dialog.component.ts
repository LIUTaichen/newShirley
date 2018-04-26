import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MaintenanceContractor } from './maintenance-contractor.model';
import { MaintenanceContractorPopupService } from './maintenance-contractor-popup.service';
import { MaintenanceContractorService } from './maintenance-contractor.service';

@Component({
    selector: 'jhi-maintenance-contractor-dialog',
    templateUrl: './maintenance-contractor-dialog.component.html'
})
export class MaintenanceContractorDialogComponent implements OnInit {

    maintenanceContractor: MaintenanceContractor;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private maintenanceContractorService: MaintenanceContractorService,
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
        if (this.maintenanceContractor.id !== undefined) {
            this.subscribeToSaveResponse(
                this.maintenanceContractorService.update(this.maintenanceContractor));
        } else {
            this.subscribeToSaveResponse(
                this.maintenanceContractorService.create(this.maintenanceContractor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<MaintenanceContractor>>) {
        result.subscribe((res: HttpResponse<MaintenanceContractor>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: MaintenanceContractor) {
        this.eventManager.broadcast({ name: 'maintenanceContractorListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-maintenance-contractor-popup',
    template: ''
})
export class MaintenanceContractorPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private maintenanceContractorPopupService: MaintenanceContractorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.maintenanceContractorPopupService
                    .open(MaintenanceContractorDialogComponent as Component, params['id']);
            } else {
                this.maintenanceContractorPopupService
                    .open(MaintenanceContractorDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
