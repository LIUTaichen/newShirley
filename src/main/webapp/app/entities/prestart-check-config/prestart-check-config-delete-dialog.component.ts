import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PrestartCheckConfig } from './prestart-check-config.model';
import { PrestartCheckConfigPopupService } from './prestart-check-config-popup.service';
import { PrestartCheckConfigService } from './prestart-check-config.service';

@Component({
    selector: 'jhi-prestart-check-config-delete-dialog',
    templateUrl: './prestart-check-config-delete-dialog.component.html'
})
export class PrestartCheckConfigDeleteDialogComponent {

    prestartCheckConfig: PrestartCheckConfig;

    constructor(
        private prestartCheckConfigService: PrestartCheckConfigService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.prestartCheckConfigService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'prestartCheckConfigListModification',
                content: 'Deleted an prestartCheckConfig'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-prestart-check-config-delete-popup',
    template: ''
})
export class PrestartCheckConfigDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prestartCheckConfigPopupService: PrestartCheckConfigPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.prestartCheckConfigPopupService
                .open(PrestartCheckConfigDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
