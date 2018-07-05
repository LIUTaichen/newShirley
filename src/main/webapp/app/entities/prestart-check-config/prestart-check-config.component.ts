import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PrestartCheckConfig } from './prestart-check-config.model';
import { PrestartCheckConfigService } from './prestart-check-config.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-prestart-check-config',
    templateUrl: './prestart-check-config.component.html'
})
export class PrestartCheckConfigComponent implements OnInit, OnDestroy {
prestartCheckConfigs: PrestartCheckConfig[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private prestartCheckConfigService: PrestartCheckConfigService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.prestartCheckConfigService.query().subscribe(
            (res: HttpResponse<PrestartCheckConfig[]>) => {
                this.prestartCheckConfigs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPrestartCheckConfigs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PrestartCheckConfig) {
        return item.id;
    }
    registerChangeInPrestartCheckConfigs() {
        this.eventSubscriber = this.eventManager.subscribe('prestartCheckConfigListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
