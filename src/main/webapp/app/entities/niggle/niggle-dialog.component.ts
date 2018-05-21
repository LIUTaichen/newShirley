import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Niggle } from './niggle.model';
import { NigglePopupService } from './niggle-popup.service';
import { NiggleService } from './niggle.service';
import { PurchaseOrder, PurchaseOrderService } from '../purchase-order';
import { Plant, PlantService } from '../plant';
import { MaintenanceContractor, MaintenanceContractorService } from '../maintenance-contractor';

@Component({
    selector: 'jhi-niggle-dialog',
    templateUrl: './niggle-dialog.component.html'
})
export class NiggleDialogComponent implements OnInit {

    niggle: Niggle;
    isSaving: boolean;

    purchaseorders: PurchaseOrder[];

    plants: Plant[];

    maintenancecontractors: MaintenanceContractor[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private niggleService: NiggleService,
        private purchaseOrderService: PurchaseOrderService,
        private plantService: PlantService,
        private maintenanceContractorService: MaintenanceContractorService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.purchaseOrderService
            .query({filter: 'niggle-is-null'})
            .subscribe((res: HttpResponse<PurchaseOrder[]>) => {
                if (!this.niggle.purchaseOrder || !this.niggle.purchaseOrder.id) {
                    this.purchaseorders = res.body;
                } else {
                    this.purchaseOrderService
                        .find(this.niggle.purchaseOrder.id)
                        .subscribe((subRes: HttpResponse<PurchaseOrder>) => {
                            this.purchaseorders = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.plantService.query()
            .subscribe((res: HttpResponse<Plant[]>) => { this.plants = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.maintenanceContractorService.query()
            .subscribe((res: HttpResponse<MaintenanceContractor[]>) => { this.maintenancecontractors = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.niggle.id !== undefined) {
            this.subscribeToSaveResponse(
                this.niggleService.update(this.niggle));
        } else {
            this.subscribeToSaveResponse(
                this.niggleService.create(this.niggle));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Niggle>>) {
        result.subscribe((res: HttpResponse<Niggle>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Niggle) {
        this.eventManager.broadcast({ name: 'niggleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPurchaseOrderById(index: number, item: PurchaseOrder) {
        return item.id;
    }

    trackPlantById(index: number, item: Plant) {
        return item.id;
    }

    trackMaintenanceContractorById(index: number, item: MaintenanceContractor) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-niggle-popup',
    template: ''
})
export class NigglePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private nigglePopupService: NigglePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.nigglePopupService
                    .open(NiggleDialogComponent as Component, params['id']);
            } else {
                this.nigglePopupService
                    .open(NiggleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
