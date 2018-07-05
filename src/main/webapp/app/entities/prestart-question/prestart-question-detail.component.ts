import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PrestartQuestion } from './prestart-question.model';
import { PrestartQuestionService } from './prestart-question.service';

@Component({
    selector: 'jhi-prestart-question-detail',
    templateUrl: './prestart-question-detail.component.html'
})
export class PrestartQuestionDetailComponent implements OnInit, OnDestroy {

    prestartQuestion: PrestartQuestion;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private prestartQuestionService: PrestartQuestionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPrestartQuestions();
    }

    load(id) {
        this.prestartQuestionService.find(id)
            .subscribe((prestartQuestionResponse: HttpResponse<PrestartQuestion>) => {
                this.prestartQuestion = prestartQuestionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPrestartQuestions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'prestartQuestionListModification',
            (response) => this.load(this.prestartQuestion.id)
        );
    }
}
