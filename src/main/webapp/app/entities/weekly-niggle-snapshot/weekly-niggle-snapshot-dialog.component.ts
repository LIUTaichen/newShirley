import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WeeklyNiggleSnapshot } from './weekly-niggle-snapshot.model';
import { WeeklyNiggleSnapshotPopupService } from './weekly-niggle-snapshot-popup.service';
import { WeeklyNiggleSnapshotService } from './weekly-niggle-snapshot.service';

@Component({
    selector: 'jhi-weekly-niggle-snapshot-dialog',
    templateUrl: './weekly-niggle-snapshot-dialog.component.html'
})
export class WeeklyNiggleSnapshotDialogComponent implements OnInit {

    weeklyNiggleSnapshot: WeeklyNiggleSnapshot;
    isSaving: boolean;
    weekEndingOnDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private weeklyNiggleSnapshotService: WeeklyNiggleSnapshotService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.weeklyNiggleSnapshot.id !== undefined) {
            this.subscribeToSaveResponse(
                this.weeklyNiggleSnapshotService.update(this.weeklyNiggleSnapshot));
        } else {
            this.subscribeToSaveResponse(
                this.weeklyNiggleSnapshotService.create(this.weeklyNiggleSnapshot));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<WeeklyNiggleSnapshot>>) {
        result.subscribe((res: HttpResponse<WeeklyNiggleSnapshot>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: WeeklyNiggleSnapshot) {
        this.eventManager.broadcast({ name: 'weeklyNiggleSnapshotListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-weekly-niggle-snapshot-popup',
    template: ''
})
export class WeeklyNiggleSnapshotPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private weeklyNiggleSnapshotPopupService: WeeklyNiggleSnapshotPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.weeklyNiggleSnapshotPopupService
                    .open(WeeklyNiggleSnapshotDialogComponent as Component, params['id']);
            } else {
                this.weeklyNiggleSnapshotPopupService
                    .open(WeeklyNiggleSnapshotDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
