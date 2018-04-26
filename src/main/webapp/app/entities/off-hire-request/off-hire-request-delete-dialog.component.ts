import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OffHireRequest } from './off-hire-request.model';
import { OffHireRequestPopupService } from './off-hire-request-popup.service';
import { OffHireRequestService } from './off-hire-request.service';

@Component({
    selector: 'jhi-off-hire-request-delete-dialog',
    templateUrl: './off-hire-request-delete-dialog.component.html'
})
export class OffHireRequestDeleteDialogComponent {

    offHireRequest: OffHireRequest;

    constructor(
        private offHireRequestService: OffHireRequestService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.offHireRequestService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'offHireRequestListModification',
                content: 'Deleted an offHireRequest'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-off-hire-request-delete-popup',
    template: ''
})
export class OffHireRequestDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private offHireRequestPopupService: OffHireRequestPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.offHireRequestPopupService
                .open(OffHireRequestDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
