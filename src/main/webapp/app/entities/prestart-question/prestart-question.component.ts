import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PrestartQuestion } from './prestart-question.model';
import { PrestartQuestionService } from './prestart-question.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-prestart-question',
    templateUrl: './prestart-question.component.html'
})
export class PrestartQuestionComponent implements OnInit, OnDestroy {
prestartQuestions: PrestartQuestion[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private prestartQuestionService: PrestartQuestionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.prestartQuestionService.query().subscribe(
            (res: HttpResponse<PrestartQuestion[]>) => {
                this.prestartQuestions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPrestartQuestions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PrestartQuestion) {
        return item.id;
    }
    registerChangeInPrestartQuestions() {
        this.eventSubscriber = this.eventManager.subscribe('prestartQuestionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
