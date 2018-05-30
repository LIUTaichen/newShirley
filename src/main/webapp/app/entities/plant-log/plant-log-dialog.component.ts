import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PlantLog } from './plant-log.model';
import { PlantLogPopupService } from './plant-log-popup.service';
import { PlantLogService } from './plant-log.service';
import { Plant, PlantService } from '../plant';
import { People, PeopleService } from '../people';
import { Project, ProjectService } from '../project';

@Component({
    selector: 'jhi-plant-log-dialog',
    templateUrl: './plant-log-dialog.component.html'
})
export class PlantLogDialogComponent implements OnInit {

    plantLog: PlantLog;
    isSaving: boolean;

    plants: Plant[];

    people: People[];

    projects: Project[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private plantLogService: PlantLogService,
        private plantService: PlantService,
        private peopleService: PeopleService,
        private projectService: ProjectService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.plantService.query()
            .subscribe((res: HttpResponse<Plant[]>) => { this.plants = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.peopleService.query()
            .subscribe((res: HttpResponse<People[]>) => { this.people = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.projectService.query()
            .subscribe((res: HttpResponse<Project[]>) => { this.projects = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.plantLog.id !== undefined) {
            this.subscribeToSaveResponse(
                this.plantLogService.update(this.plantLog));
        } else {
            this.subscribeToSaveResponse(
                this.plantLogService.create(this.plantLog));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PlantLog>>) {
        result.subscribe((res: HttpResponse<PlantLog>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PlantLog) {
        this.eventManager.broadcast({ name: 'plantLogListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPlantById(index: number, item: Plant) {
        return item.id;
    }

    trackPeopleById(index: number, item: People) {
        return item.id;
    }

    trackProjectById(index: number, item: Project) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-plant-log-popup',
    template: ''
})
export class PlantLogPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plantLogPopupService: PlantLogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.plantLogPopupService
                    .open(PlantLogDialogComponent as Component, params['id']);
            } else {
                this.plantLogPopupService
                    .open(PlantLogDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
