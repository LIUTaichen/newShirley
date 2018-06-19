import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WeeklyNiggleSnapshot } from './weekly-niggle-snapshot.model';
import { WeeklyNiggleSnapshotPopupService } from './weekly-niggle-snapshot-popup.service';
import { WeeklyNiggleSnapshotService } from './weekly-niggle-snapshot.service';

@Component({
    selector: 'jhi-weekly-niggle-snapshot-delete-dialog',
    templateUrl: './weekly-niggle-snapshot-delete-dialog.component.html'
})
export class WeeklyNiggleSnapshotDeleteDialogComponent {

    weeklyNiggleSnapshot: WeeklyNiggleSnapshot;

    constructor(
        private weeklyNiggleSnapshotService: WeeklyNiggleSnapshotService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.weeklyNiggleSnapshotService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'weeklyNiggleSnapshotListModification',
                content: 'Deleted an weeklyNiggleSnapshot'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-weekly-niggle-snapshot-delete-popup',
    template: ''
})
export class WeeklyNiggleSnapshotDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private weeklyNiggleSnapshotPopupService: WeeklyNiggleSnapshotPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.weeklyNiggleSnapshotPopupService
                .open(WeeklyNiggleSnapshotDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
