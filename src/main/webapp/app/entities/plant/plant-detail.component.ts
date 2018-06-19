import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Plant } from './plant.model';
import { PlantService } from './plant.service';

@Component({
    selector: 'jhi-plant-detail',
    templateUrl: './plant-detail.component.html'
})
export class PlantDetailComponent implements OnInit, OnDestroy {

    plant: Plant;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private plantService: PlantService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlants();
    }

    load(id) {
        this.plantService.find(id)
            .subscribe((plantResponse: HttpResponse<Plant>) => {
                this.plant = plantResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'plantListModification',
            (response) => this.load(this.plant.id)
        );
    }
}
