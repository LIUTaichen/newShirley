import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PrestartQuestionOption } from './prestart-question-option.model';
import { PrestartQuestionOptionService } from './prestart-question-option.service';

@Component({
    selector: 'jhi-prestart-question-option-detail',
    templateUrl: './prestart-question-option-detail.component.html'
})
export class PrestartQuestionOptionDetailComponent implements OnInit, OnDestroy {

    prestartQuestionOption: PrestartQuestionOption;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private prestartQuestionOptionService: PrestartQuestionOptionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPrestartQuestionOptions();
    }

    load(id) {
        this.prestartQuestionOptionService.find(id)
            .subscribe((prestartQuestionOptionResponse: HttpResponse<PrestartQuestionOption>) => {
                this.prestartQuestionOption = prestartQuestionOptionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPrestartQuestionOptions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'prestartQuestionOptionListModification',
            (response) => this.load(this.prestartQuestionOption.id)
        );
    }
}
