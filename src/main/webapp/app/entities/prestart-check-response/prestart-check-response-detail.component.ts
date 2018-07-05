import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PrestartCheckResponse } from './prestart-check-response.model';
import { PrestartCheckResponseService } from './prestart-check-response.service';

@Component({
    selector: 'jhi-prestart-check-response-detail',
    templateUrl: './prestart-check-response-detail.component.html'
})
export class PrestartCheckResponseDetailComponent implements OnInit, OnDestroy {

    prestartCheckResponse: PrestartCheckResponse;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private prestartCheckResponseService: PrestartCheckResponseService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPrestartCheckResponses();
    }

    load(id) {
        this.prestartCheckResponseService.find(id)
            .subscribe((prestartCheckResponseResponse: HttpResponse<PrestartCheckResponse>) => {
                this.prestartCheckResponse = prestartCheckResponseResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPrestartCheckResponses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'prestartCheckResponseListModification',
            (response) => this.load(this.prestartCheckResponse.id)
        );
    }
}
