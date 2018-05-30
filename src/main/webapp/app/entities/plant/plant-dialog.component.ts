import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Plant } from './plant.model';
import { PlantPopupService } from './plant-popup.service';
import { PlantService } from './plant.service';
import { Location, LocationService } from '../location';
import { Category, CategoryService } from '../category';
import { Company, CompanyService } from '../company';
import { MaintenanceContractor, MaintenanceContractorService } from '../maintenance-contractor';

@Component({
    selector: 'jhi-plant-dialog',
    templateUrl: './plant-dialog.component.html'
})
export class PlantDialogComponent implements OnInit {

    plant: Plant;
    isSaving: boolean;

    locations: Location[];

    categories: Category[];

    companies: Company[];

    maintenancecontractors: MaintenanceContractor[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private plantService: PlantService,
        private locationService: LocationService,
        private categoryService: CategoryService,
        private companyService: CompanyService,
        private maintenanceContractorService: MaintenanceContractorService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.locationService
            .query({filter: 'plant-is-null'})
            .subscribe((res: HttpResponse<Location[]>) => {
                if (!this.plant.location || !this.plant.location.id) {
                    this.locations = res.body;
                } else {
                    this.locationService
                        .find(this.plant.location.id)
                        .subscribe((subRes: HttpResponse<Location>) => {
                            this.locations = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.categoryService.query()
            .subscribe((res: HttpResponse<Category[]>) => { this.categories = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.companyService.query()
            .subscribe((res: HttpResponse<Company[]>) => { this.companies = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.maintenanceContractorService.query()
            .subscribe((res: HttpResponse<MaintenanceContractor[]>) => { this.maintenancecontractors = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.plant.id !== undefined) {
            this.subscribeToSaveResponse(
                this.plantService.update(this.plant));
        } else {
            this.subscribeToSaveResponse(
                this.plantService.create(this.plant));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Plant>>) {
        result.subscribe((res: HttpResponse<Plant>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Plant) {
        this.eventManager.broadcast({ name: 'plantListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackLocationById(index: number, item: Location) {
        return item.id;
    }

    trackCategoryById(index: number, item: Category) {
        return item.id;
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }

    trackMaintenanceContractorById(index: number, item: MaintenanceContractor) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-plant-popup',
    template: ''
})
export class PlantPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plantPopupService: PlantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.plantPopupService
                    .open(PlantDialogComponent as Component, params['id']);
            } else {
                this.plantPopupService
                    .open(PlantDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
