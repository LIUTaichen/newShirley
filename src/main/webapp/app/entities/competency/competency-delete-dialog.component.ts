import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Competency } from './competency.model';
import { CompetencyPopupService } from './competency-popup.service';
import { CompetencyService } from './competency.service';

@Component({
    selector: 'jhi-competency-delete-dialog',
    templateUrl: './competency-delete-dialog.component.html'
})
export class CompetencyDeleteDialogComponent {

    competency: Competency;

    constructor(
        private competencyService: CompetencyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.competencyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'competencyListModification',
                content: 'Deleted an competency'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-competency-delete-popup',
    template: ''
})
export class CompetencyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private competencyPopupService: CompetencyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.competencyPopupService
                .open(CompetencyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
