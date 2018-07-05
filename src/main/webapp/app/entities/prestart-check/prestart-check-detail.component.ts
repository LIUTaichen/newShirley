import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { PrestartCheck } from './prestart-check.model';
import { PrestartCheckService } from './prestart-check.service';

@Component({
    selector: 'jhi-prestart-check-detail',
    templateUrl: './prestart-check-detail.component.html'
})
export class PrestartCheckDetailComponent implements OnInit, OnDestroy {

    prestartCheck: PrestartCheck;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private prestartCheckService: PrestartCheckService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPrestartChecks();
    }

    load(id) {
        this.prestartCheckService.find(id)
            .subscribe((prestartCheckResponse: HttpResponse<PrestartCheck>) => {
                this.prestartCheck = prestartCheckResponse.body;
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

    registerChangeInPrestartChecks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'prestartCheckListModification',
            (response) => this.load(this.prestartCheck.id)
        );
    }
}
