import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PrestartCheckConfig } from './prestart-check-config.model';
import { PrestartCheckConfigPopupService } from './prestart-check-config-popup.service';
import { PrestartCheckConfigService } from './prestart-check-config.service';

@Component({
    selector: 'jhi-prestart-check-config-dialog',
    templateUrl: './prestart-check-config-dialog.component.html'
})
export class PrestartCheckConfigDialogComponent implements OnInit {

    prestartCheckConfig: PrestartCheckConfig;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private prestartCheckConfigService: PrestartCheckConfigService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.prestartCheckConfig.id !== undefined) {
            this.subscribeToSaveResponse(
                this.prestartCheckConfigService.update(this.prestartCheckConfig));
        } else {
            this.subscribeToSaveResponse(
                this.prestartCheckConfigService.create(this.prestartCheckConfig));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PrestartCheckConfig>>) {
        result.subscribe((res: HttpResponse<PrestartCheckConfig>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PrestartCheckConfig) {
        this.eventManager.broadcast({ name: 'prestartCheckConfigListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-prestart-check-config-popup',
    template: ''
})
export class PrestartCheckConfigPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prestartCheckConfigPopupService: PrestartCheckConfigPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.prestartCheckConfigPopupService
                    .open(PrestartCheckConfigDialogComponent as Component, params['id']);
            } else {
                this.prestartCheckConfigPopupService
                    .open(PrestartCheckConfigDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
