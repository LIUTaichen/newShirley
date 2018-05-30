import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PlantLog } from './plant-log.model';
import { PlantLogService } from './plant-log.service';

@Component({
    selector: 'jhi-plant-log-detail',
    templateUrl: './plant-log-detail.component.html'
})
export class PlantLogDetailComponent implements OnInit, OnDestroy {

    plantLog: PlantLog;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private plantLogService: PlantLogService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlantLogs();
    }

    load(id) {
        this.plantLogService.find(id)
            .subscribe((plantLogResponse: HttpResponse<PlantLog>) => {
                this.plantLog = plantLogResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlantLogs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'plantLogListModification',
            (response) => this.load(this.plantLog.id)
        );
    }
}
