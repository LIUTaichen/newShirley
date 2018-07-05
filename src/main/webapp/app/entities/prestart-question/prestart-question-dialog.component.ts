import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PrestartQuestion } from './prestart-question.model';
import { PrestartQuestionPopupService } from './prestart-question-popup.service';
import { PrestartQuestionService } from './prestart-question.service';

@Component({
    selector: 'jhi-prestart-question-dialog',
    templateUrl: './prestart-question-dialog.component.html'
})
export class PrestartQuestionDialogComponent implements OnInit {

    prestartQuestion: PrestartQuestion;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private prestartQuestionService: PrestartQuestionService,
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
        if (this.prestartQuestion.id !== undefined) {
            this.subscribeToSaveResponse(
                this.prestartQuestionService.update(this.prestartQuestion));
        } else {
            this.subscribeToSaveResponse(
                this.prestartQuestionService.create(this.prestartQuestion));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PrestartQuestion>>) {
        result.subscribe((res: HttpResponse<PrestartQuestion>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PrestartQuestion) {
        this.eventManager.broadcast({ name: 'prestartQuestionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-prestart-question-popup',
    template: ''
})
export class PrestartQuestionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prestartQuestionPopupService: PrestartQuestionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.prestartQuestionPopupService
                    .open(PrestartQuestionDialogComponent as Component, params['id']);
            } else {
                this.prestartQuestionPopupService
                    .open(PrestartQuestionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
