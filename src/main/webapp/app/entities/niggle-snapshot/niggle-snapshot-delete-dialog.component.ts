import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { NiggleSnapshot } from './niggle-snapshot.model';
import { NiggleSnapshotPopupService } from './niggle-snapshot-popup.service';
import { NiggleSnapshotService } from './niggle-snapshot.service';

@Component({
    selector: 'jhi-niggle-snapshot-delete-dialog',
    templateUrl: './niggle-snapshot-delete-dialog.component.html'
})
export class NiggleSnapshotDeleteDialogComponent {

    niggleSnapshot: NiggleSnapshot;

    constructor(
        private niggleSnapshotService: NiggleSnapshotService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.niggleSnapshotService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'niggleSnapshotListModification',
                content: 'Deleted an niggleSnapshot'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-niggle-snapshot-delete-popup',
    template: ''
})
export class NiggleSnapshotDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private niggleSnapshotPopupService: NiggleSnapshotPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.niggleSnapshotPopupService
                .open(NiggleSnapshotDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
