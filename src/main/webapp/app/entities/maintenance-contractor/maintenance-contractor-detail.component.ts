import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { MaintenanceContractor } from './maintenance-contractor.model';
import { MaintenanceContractorService } from './maintenance-contractor.service';

@Component({
    selector: 'jhi-maintenance-contractor-detail',
    templateUrl: './maintenance-contractor-detail.component.html'
})
export class MaintenanceContractorDetailComponent implements OnInit, OnDestroy {

    maintenanceContractor: MaintenanceContractor;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private maintenanceContractorService: MaintenanceContractorService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMaintenanceContractors();
    }

    load(id) {
        this.maintenanceContractorService.find(id)
            .subscribe((maintenanceContractorResponse: HttpResponse<MaintenanceContractor>) => {
                this.maintenanceContractor = maintenanceContractorResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMaintenanceContractors() {
        this.eventSubscriber = this.eventManager.subscribe(
            'maintenanceContractorListModification',
            (response) => this.load(this.maintenanceContractor.id)
        );
    }
}
