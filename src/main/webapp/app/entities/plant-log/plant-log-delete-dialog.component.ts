import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PlantLog } from './plant-log.model';
import { PlantLogPopupService } from './plant-log-popup.service';
import { PlantLogService } from './plant-log.service';

@Component({
    selector: 'jhi-plant-log-delete-dialog',
    templateUrl: './plant-log-delete-dialog.component.html'
})
export class PlantLogDeleteDialogComponent {

    plantLog: PlantLog;

    constructor(
        private plantLogService: PlantLogService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.plantLogService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'plantLogListModification',
                content: 'Deleted an plantLog'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-plant-log-delete-popup',
    template: ''
})
export class PlantLogDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plantLogPopupService: PlantLogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.plantLogPopupService
                .open(PlantLogDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
