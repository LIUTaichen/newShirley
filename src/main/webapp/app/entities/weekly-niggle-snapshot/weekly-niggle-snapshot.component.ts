import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WeeklyNiggleSnapshot } from './weekly-niggle-snapshot.model';
import { WeeklyNiggleSnapshotService } from './weekly-niggle-snapshot.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-weekly-niggle-snapshot',
    templateUrl: './weekly-niggle-snapshot.component.html'
})
export class WeeklyNiggleSnapshotComponent implements OnInit, OnDestroy {
weeklyNiggleSnapshots: WeeklyNiggleSnapshot[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private weeklyNiggleSnapshotService: WeeklyNiggleSnapshotService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.weeklyNiggleSnapshotService.query().subscribe(
            (res: HttpResponse<WeeklyNiggleSnapshot[]>) => {
                this.weeklyNiggleSnapshots = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInWeeklyNiggleSnapshots();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: WeeklyNiggleSnapshot) {
        return item.id;
    }
    registerChangeInWeeklyNiggleSnapshots() {
        this.eventSubscriber = this.eventManager.subscribe('weeklyNiggleSnapshotListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
