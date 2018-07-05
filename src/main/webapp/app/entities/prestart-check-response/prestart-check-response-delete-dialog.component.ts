import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PrestartCheckResponse } from './prestart-check-response.model';
import { PrestartCheckResponsePopupService } from './prestart-check-response-popup.service';
import { PrestartCheckResponseService } from './prestart-check-response.service';

@Component({
    selector: 'jhi-prestart-check-response-delete-dialog',
    templateUrl: './prestart-check-response-delete-dialog.component.html'
})
export class PrestartCheckResponseDeleteDialogComponent {

    prestartCheckResponse: PrestartCheckResponse;

    constructor(
        private prestartCheckResponseService: PrestartCheckResponseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.prestartCheckResponseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'prestartCheckResponseListModification',
                content: 'Deleted an prestartCheckResponse'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-prestart-check-response-delete-popup',
    template: ''
})
export class PrestartCheckResponseDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prestartCheckResponsePopupService: PrestartCheckResponsePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.prestartCheckResponsePopupService
                .open(PrestartCheckResponseDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
