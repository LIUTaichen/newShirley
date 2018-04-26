import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Competency } from './competency.model';
import { CompetencyService } from './competency.service';

@Component({
    selector: 'jhi-competency-detail',
    templateUrl: './competency-detail.component.html'
})
export class CompetencyDetailComponent implements OnInit, OnDestroy {

    competency: Competency;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private competencyService: CompetencyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompetencies();
    }

    load(id) {
        this.competencyService.find(id)
            .subscribe((competencyResponse: HttpResponse<Competency>) => {
                this.competency = competencyResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompetencies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'competencyListModification',
            (response) => this.load(this.competency.id)
        );
    }
}
