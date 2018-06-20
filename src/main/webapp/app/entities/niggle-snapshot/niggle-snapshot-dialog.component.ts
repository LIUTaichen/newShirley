import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { NiggleSnapshot } from './niggle-snapshot.model';
import { NiggleSnapshotPopupService } from './niggle-snapshot-popup.service';
import { NiggleSnapshotService } from './niggle-snapshot.service';

@Component({
    selector: 'jhi-niggle-snapshot-dialog',
    templateUrl: './niggle-snapshot-dialog.component.html'
})
export class NiggleSnapshotDialogComponent implements OnInit {

    niggleSnapshot: NiggleSnapshot;
    isSaving: boolean;
    dateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private niggleSnapshotService: NiggleSnapshotService,
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
        if (this.niggleSnapshot.id !== undefined) {
            this.subscribeToSaveResponse(
                this.niggleSnapshotService.update(this.niggleSnapshot));
        } else {
            this.subscribeToSaveResponse(
                this.niggleSnapshotService.create(this.niggleSnapshot));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<NiggleSnapshot>>) {
        result.subscribe((res: HttpResponse<NiggleSnapshot>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: NiggleSnapshot) {
        this.eventManager.broadcast({ name: 'niggleSnapshotListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-niggle-snapshot-popup',
    template: ''
})
export class NiggleSnapshotPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private niggleSnapshotPopupService: NiggleSnapshotPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.niggleSnapshotPopupService
                    .open(NiggleSnapshotDialogComponent as Component, params['id']);
            } else {
                this.niggleSnapshotPopupService
                    .open(NiggleSnapshotDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
