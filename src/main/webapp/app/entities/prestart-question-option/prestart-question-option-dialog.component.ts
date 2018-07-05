import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PrestartQuestionOption } from './prestart-question-option.model';
import { PrestartQuestionOptionPopupService } from './prestart-question-option-popup.service';
import { PrestartQuestionOptionService } from './prestart-question-option.service';
import { PrestartQuestion, PrestartQuestionService } from '../prestart-question';

@Component({
    selector: 'jhi-prestart-question-option-dialog',
    templateUrl: './prestart-question-option-dialog.component.html'
})
export class PrestartQuestionOptionDialogComponent implements OnInit {

    prestartQuestionOption: PrestartQuestionOption;
    isSaving: boolean;

    prestartquestions: PrestartQuestion[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private prestartQuestionOptionService: PrestartQuestionOptionService,
        private prestartQuestionService: PrestartQuestionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.prestartQuestionService.query()
            .subscribe((res: HttpResponse<PrestartQuestion[]>) => { this.prestartquestions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.prestartQuestionOption.id !== undefined) {
            this.subscribeToSaveResponse(
                this.prestartQuestionOptionService.update(this.prestartQuestionOption));
        } else {
            this.subscribeToSaveResponse(
                this.prestartQuestionOptionService.create(this.prestartQuestionOption));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PrestartQuestionOption>>) {
        result.subscribe((res: HttpResponse<PrestartQuestionOption>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PrestartQuestionOption) {
        this.eventManager.broadcast({ name: 'prestartQuestionOptionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPrestartQuestionById(index: number, item: PrestartQuestion) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-prestart-question-option-popup',
    template: ''
})
export class PrestartQuestionOptionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prestartQuestionOptionPopupService: PrestartQuestionOptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.prestartQuestionOptionPopupService
                    .open(PrestartQuestionOptionDialogComponent as Component, params['id']);
            } else {
                this.prestartQuestionOptionPopupService
                    .open(PrestartQuestionOptionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
