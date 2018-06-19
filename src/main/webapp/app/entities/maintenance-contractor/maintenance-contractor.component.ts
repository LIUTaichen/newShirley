import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MaintenanceContractor } from './maintenance-contractor.model';
import { MaintenanceContractorService } from './maintenance-contractor.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-maintenance-contractor',
    templateUrl: './maintenance-contractor.component.html'
})
export class MaintenanceContractorComponent implements OnInit, OnDestroy {
maintenanceContractors: MaintenanceContractor[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private maintenanceContractorService: MaintenanceContractorService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.maintenanceContractorService.query().subscribe(
            (res: HttpResponse<MaintenanceContractor[]>) => {
                this.maintenanceContractors = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMaintenanceContractors();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MaintenanceContractor) {
        return item.id;
    }
    registerChangeInMaintenanceContractors() {
        this.eventSubscriber = this.eventManager.subscribe('maintenanceContractorListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
