import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Plant } from './plant.model';
import { PlantPopupService } from './plant-popup.service';
import { PlantService } from './plant.service';

@Component({
    selector: 'jhi-plant-delete-dialog',
    templateUrl: './plant-delete-dialog.component.html'
})
export class PlantDeleteDialogComponent {

    plant: Plant;

    constructor(
        private plantService: PlantService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.plantService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'plantListModification',
                content: 'Deleted an plant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-plant-delete-popup',
    template: ''
})
export class PlantDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plantPopupService: PlantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.plantPopupService
                .open(PlantDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
