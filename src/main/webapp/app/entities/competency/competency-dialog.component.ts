import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Competency } from './competency.model';
import { CompetencyPopupService } from './competency-popup.service';
import { CompetencyService } from './competency.service';

@Component({
    selector: 'jhi-competency-dialog',
    templateUrl: './competency-dialog.component.html'
})
export class CompetencyDialogComponent implements OnInit {

    competency: Competency;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private competencyService: CompetencyService,
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
        if (this.competency.id !== undefined) {
            this.subscribeToSaveResponse(
                this.competencyService.update(this.competency));
        } else {
            this.subscribeToSaveResponse(
                this.competencyService.create(this.competency));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Competency>>) {
        result.subscribe((res: HttpResponse<Competency>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Competency) {
        this.eventManager.broadcast({ name: 'competencyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-competency-popup',
    template: ''
})
export class CompetencyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private competencyPopupService: CompetencyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.competencyPopupService
                    .open(CompetencyDialogComponent as Component, params['id']);
            } else {
                this.competencyPopupService
                    .open(CompetencyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
