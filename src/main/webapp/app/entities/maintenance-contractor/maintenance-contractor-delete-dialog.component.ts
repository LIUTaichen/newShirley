import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MaintenanceContractor } from './maintenance-contractor.model';
import { MaintenanceContractorPopupService } from './maintenance-contractor-popup.service';
import { MaintenanceContractorService } from './maintenance-contractor.service';

@Component({
    selector: 'jhi-maintenance-contractor-delete-dialog',
    templateUrl: './maintenance-contractor-delete-dialog.component.html'
})
export class MaintenanceContractorDeleteDialogComponent {

    maintenanceContractor: MaintenanceContractor;

    constructor(
        private maintenanceContractorService: MaintenanceContractorService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.maintenanceContractorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'maintenanceContractorListModification',
                content: 'Deleted an maintenanceContractor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-maintenance-contractor-delete-popup',
    template: ''
})
export class MaintenanceContractorDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private maintenanceContractorPopupService: MaintenanceContractorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.maintenanceContractorPopupService
                .open(MaintenanceContractorDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
