import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { PrestartCheck } from './prestart-check.model';
import { PrestartCheckPopupService } from './prestart-check-popup.service';
import { PrestartCheckService } from './prestart-check.service';
import { Project, ProjectService } from '../project';
import { Plant, PlantService } from '../plant';
import { Location, LocationService } from '../location';
import { People, PeopleService } from '../people';

@Component({
    selector: 'jhi-prestart-check-dialog',
    templateUrl: './prestart-check-dialog.component.html'
})
export class PrestartCheckDialogComponent implements OnInit {

    prestartCheck: PrestartCheck;
    isSaving: boolean;

    projects: Project[];

    plants: Plant[];

    locations: Location[];

    people: People[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private prestartCheckService: PrestartCheckService,
        private projectService: ProjectService,
        private plantService: PlantService,
        private locationService: LocationService,
        private peopleService: PeopleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.projectService.query()
            .subscribe((res: HttpResponse<Project[]>) => { this.projects = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.plantService.query()
            .subscribe((res: HttpResponse<Plant[]>) => { this.plants = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.locationService.query()
            .subscribe((res: HttpResponse<Location[]>) => { this.locations = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.peopleService.query()
            .subscribe((res: HttpResponse<People[]>) => { this.people = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.prestartCheck.id !== undefined) {
            this.subscribeToSaveResponse(
                this.prestartCheckService.update(this.prestartCheck));
        } else {
            this.subscribeToSaveResponse(
                this.prestartCheckService.create(this.prestartCheck));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PrestartCheck>>) {
        result.subscribe((res: HttpResponse<PrestartCheck>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PrestartCheck) {
        this.eventManager.broadcast({ name: 'prestartCheckListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProjectById(index: number, item: Project) {
        return item.id;
    }

    trackPlantById(index: number, item: Plant) {
        return item.id;
    }

    trackLocationById(index: number, item: Location) {
        return item.id;
    }

    trackPeopleById(index: number, item: People) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-prestart-check-popup',
    template: ''
})
export class PrestartCheckPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prestartCheckPopupService: PrestartCheckPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.prestartCheckPopupService
                    .open(PrestartCheckDialogComponent as Component, params['id']);
            } else {
                this.prestartCheckPopupService
                    .open(PrestartCheckDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
