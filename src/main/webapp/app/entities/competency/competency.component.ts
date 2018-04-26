import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Competency } from './competency.model';
import { CompetencyService } from './competency.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-competency',
    templateUrl: './competency.component.html'
})
export class CompetencyComponent implements OnInit, OnDestroy {
competencies: Competency[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private competencyService: CompetencyService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.competencyService.query().subscribe(
            (res: HttpResponse<Competency[]>) => {
                this.competencies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCompetencies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Competency) {
        return item.id;
    }
    registerChangeInCompetencies() {
        this.eventSubscriber = this.eventManager.subscribe('competencyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
