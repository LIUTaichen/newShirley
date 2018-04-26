import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Niggle } from './niggle.model';
import { NigglePopupService } from './niggle-popup.service';
import { NiggleService } from './niggle.service';

@Component({
    selector: 'jhi-niggle-delete-dialog',
    templateUrl: './niggle-delete-dialog.component.html'
})
export class NiggleDeleteDialogComponent {

    niggle: Niggle;

    constructor(
        private niggleService: NiggleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.niggleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'niggleListModification',
                content: 'Deleted an niggle'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-niggle-delete-popup',
    template: ''
})
export class NiggleDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private nigglePopupService: NigglePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.nigglePopupService
                .open(NiggleDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
