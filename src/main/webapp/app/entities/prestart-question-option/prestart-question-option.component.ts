import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PrestartQuestionOption } from './prestart-question-option.model';
import { PrestartQuestionOptionService } from './prestart-question-option.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-prestart-question-option',
    templateUrl: './prestart-question-option.component.html'
})
export class PrestartQuestionOptionComponent implements OnInit, OnDestroy {
prestartQuestionOptions: PrestartQuestionOption[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private prestartQuestionOptionService: PrestartQuestionOptionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.prestartQuestionOptionService.query().subscribe(
            (res: HttpResponse<PrestartQuestionOption[]>) => {
                this.prestartQuestionOptions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPrestartQuestionOptions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PrestartQuestionOption) {
        return item.id;
    }
    registerChangeInPrestartQuestionOptions() {
        this.eventSubscriber = this.eventManager.subscribe('prestartQuestionOptionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
