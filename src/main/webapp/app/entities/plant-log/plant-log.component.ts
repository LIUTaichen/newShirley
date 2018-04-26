import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PlantLog } from './plant-log.model';
import { PlantLogService } from './plant-log.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-plant-log',
    templateUrl: './plant-log.component.html'
})
export class PlantLogComponent implements OnInit, OnDestroy {
plantLogs: PlantLog[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private plantLogService: PlantLogService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.plantLogService.query().subscribe(
            (res: HttpResponse<PlantLog[]>) => {
                this.plantLogs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPlantLogs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PlantLog) {
        return item.id;
    }
    registerChangeInPlantLogs() {
        this.eventSubscriber = this.eventManager.subscribe('plantLogListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
