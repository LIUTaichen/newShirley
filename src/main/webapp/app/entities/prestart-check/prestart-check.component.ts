import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { PrestartCheck } from './prestart-check.model';
import { PrestartCheckService } from './prestart-check.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-prestart-check',
    templateUrl: './prestart-check.component.html'
})
export class PrestartCheckComponent implements OnInit, OnDestroy {
prestartChecks: PrestartCheck[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private prestartCheckService: PrestartCheckService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.prestartCheckService.query().subscribe(
            (res: HttpResponse<PrestartCheck[]>) => {
                this.prestartChecks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPrestartChecks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PrestartCheck) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInPrestartChecks() {
        this.eventSubscriber = this.eventManager.subscribe('prestartCheckListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
