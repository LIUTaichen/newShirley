import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PrestartCheckQuestionListItem } from './prestart-check-question-list-item.model';
import { PrestartCheckQuestionListItemPopupService } from './prestart-check-question-list-item-popup.service';
import { PrestartCheckQuestionListItemService } from './prestart-check-question-list-item.service';
import { PrestartCheckConfig, PrestartCheckConfigService } from '../prestart-check-config';
import { PrestartQuestion, PrestartQuestionService } from '../prestart-question';

@Component({
    selector: 'jhi-prestart-check-question-list-item-dialog',
    templateUrl: './prestart-check-question-list-item-dialog.component.html'
})
export class PrestartCheckQuestionListItemDialogComponent implements OnInit {

    prestartCheckQuestionListItem: PrestartCheckQuestionListItem;
    isSaving: boolean;

    prestartcheckconfigs: PrestartCheckConfig[];

    prestartquestions: PrestartQuestion[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private prestartCheckQuestionListItemService: PrestartCheckQuestionListItemService,
        private prestartCheckConfigService: PrestartCheckConfigService,
        private prestartQuestionService: PrestartQuestionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.prestartCheckConfigService.query()
            .subscribe((res: HttpResponse<PrestartCheckConfig[]>) => { this.prestartcheckconfigs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.prestartQuestionService.query()
            .subscribe((res: HttpResponse<PrestartQuestion[]>) => { this.prestartquestions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.prestartCheckQuestionListItem.id !== undefined) {
            this.subscribeToSaveResponse(
                this.prestartCheckQuestionListItemService.update(this.prestartCheckQuestionListItem));
        } else {
            this.subscribeToSaveResponse(
                this.prestartCheckQuestionListItemService.create(this.prestartCheckQuestionListItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PrestartCheckQuestionListItem>>) {
        result.subscribe((res: HttpResponse<PrestartCheckQuestionListItem>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PrestartCheckQuestionListItem) {
        this.eventManager.broadcast({ name: 'prestartCheckQuestionListItemListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPrestartCheckConfigById(index: number, item: PrestartCheckConfig) {
        return item.id;
    }

    trackPrestartQuestionById(index: number, item: PrestartQuestion) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-prestart-check-question-list-item-popup',
    template: ''
})
export class PrestartCheckQuestionListItemPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prestartCheckQuestionListItemPopupService: PrestartCheckQuestionListItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.prestartCheckQuestionListItemPopupService
                    .open(PrestartCheckQuestionListItemDialogComponent as Component, params['id']);
            } else {
                this.prestartCheckQuestionListItemPopupService
                    .open(PrestartCheckQuestionListItemDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
