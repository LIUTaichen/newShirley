import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PrestartCheckResponse } from './prestart-check-response.model';
import { PrestartCheckResponseService } from './prestart-check-response.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-prestart-check-response',
    templateUrl: './prestart-check-response.component.html'
})
export class PrestartCheckResponseComponent implements OnInit, OnDestroy {
prestartCheckResponses: PrestartCheckResponse[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private prestartCheckResponseService: PrestartCheckResponseService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.prestartCheckResponseService.query().subscribe(
            (res: HttpResponse<PrestartCheckResponse[]>) => {
                this.prestartCheckResponses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPrestartCheckResponses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PrestartCheckResponse) {
        return item.id;
    }
    registerChangeInPrestartCheckResponses() {
        this.eventSubscriber = this.eventManager.subscribe('prestartCheckResponseListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
