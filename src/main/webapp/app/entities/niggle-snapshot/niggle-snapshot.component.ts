import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { NiggleSnapshot } from './niggle-snapshot.model';
import { NiggleSnapshotService } from './niggle-snapshot.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-niggle-snapshot',
    templateUrl: './niggle-snapshot.component.html'
})
export class NiggleSnapshotComponent implements OnInit, OnDestroy {
niggleSnapshots: NiggleSnapshot[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private niggleSnapshotService: NiggleSnapshotService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.niggleSnapshotService.query().subscribe(
            (res: HttpResponse<NiggleSnapshot[]>) => {
                this.niggleSnapshots = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInNiggleSnapshots();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: NiggleSnapshot) {
        return item.id;
    }
    registerChangeInNiggleSnapshots() {
        this.eventSubscriber = this.eventManager.subscribe('niggleSnapshotListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
