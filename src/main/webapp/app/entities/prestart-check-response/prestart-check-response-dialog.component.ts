import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PrestartCheckResponse } from './prestart-check-response.model';
import { PrestartCheckResponsePopupService } from './prestart-check-response-popup.service';
import { PrestartCheckResponseService } from './prestart-check-response.service';
import { PrestartCheck, PrestartCheckService } from '../prestart-check';
import { PrestartQuestion, PrestartQuestionService } from '../prestart-question';
import { PrestartQuestionOption, PrestartQuestionOptionService } from '../prestart-question-option';

@Component({
    selector: 'jhi-prestart-check-response-dialog',
    templateUrl: './prestart-check-response-dialog.component.html'
})
export class PrestartCheckResponseDialogComponent implements OnInit {

    prestartCheckResponse: PrestartCheckResponse;
    isSaving: boolean;

    prestartchecks: PrestartCheck[];

    prestartquestions: PrestartQuestion[];

    prestartquestionoptions: PrestartQuestionOption[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private prestartCheckResponseService: PrestartCheckResponseService,
        private prestartCheckService: PrestartCheckService,
        private prestartQuestionService: PrestartQuestionService,
        private prestartQuestionOptionService: PrestartQuestionOptionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.prestartCheckService.query()
            .subscribe((res: HttpResponse<PrestartCheck[]>) => { this.prestartchecks = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.prestartQuestionService.query()
            .subscribe((res: HttpResponse<PrestartQuestion[]>) => { this.prestartquestions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.prestartQuestionOptionService.query()
            .subscribe((res: HttpResponse<PrestartQuestionOption[]>) => { this.prestartquestionoptions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.prestartCheckResponse.id !== undefined) {
            this.subscribeToSaveResponse(
                this.prestartCheckResponseService.update(this.prestartCheckResponse));
        } else {
            this.subscribeToSaveResponse(
                this.prestartCheckResponseService.create(this.prestartCheckResponse));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PrestartCheckResponse>>) {
        result.subscribe((res: HttpResponse<PrestartCheckResponse>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PrestartCheckResponse) {
        this.eventManager.broadcast({ name: 'prestartCheckResponseListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPrestartCheckById(index: number, item: PrestartCheck) {
        return item.id;
    }

    trackPrestartQuestionById(index: number, item: PrestartQuestion) {
        return item.id;
    }

    trackPrestartQuestionOptionById(index: number, item: PrestartQuestionOption) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-prestart-check-response-popup',
    template: ''
})
export class PrestartCheckResponsePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prestartCheckResponsePopupService: PrestartCheckResponsePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.prestartCheckResponsePopupService
                    .open(PrestartCheckResponseDialogComponent as Component, params['id']);
            } else {
                this.prestartCheckResponsePopupService
                    .open(PrestartCheckResponseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
