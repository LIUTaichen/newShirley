import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PrestartCheckConfig } from './prestart-check-config.model';
import { PrestartCheckConfigService } from './prestart-check-config.service';

@Component({
    selector: 'jhi-prestart-check-config-detail',
    templateUrl: './prestart-check-config-detail.component.html'
})
export class PrestartCheckConfigDetailComponent implements OnInit, OnDestroy {

    prestartCheckConfig: PrestartCheckConfig;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private prestartCheckConfigService: PrestartCheckConfigService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPrestartCheckConfigs();
    }

    load(id) {
        this.prestartCheckConfigService.find(id)
            .subscribe((prestartCheckConfigResponse: HttpResponse<PrestartCheckConfig>) => {
                this.prestartCheckConfig = prestartCheckConfigResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPrestartCheckConfigs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'prestartCheckConfigListModification',
            (response) => this.load(this.prestartCheckConfig.id)
        );
    }
}
