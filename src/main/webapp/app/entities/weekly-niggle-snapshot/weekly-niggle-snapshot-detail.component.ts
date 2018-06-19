import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { WeeklyNiggleSnapshot } from './weekly-niggle-snapshot.model';
import { WeeklyNiggleSnapshotService } from './weekly-niggle-snapshot.service';

@Component({
    selector: 'jhi-weekly-niggle-snapshot-detail',
    templateUrl: './weekly-niggle-snapshot-detail.component.html'
})
export class WeeklyNiggleSnapshotDetailComponent implements OnInit, OnDestroy {

    weeklyNiggleSnapshot: WeeklyNiggleSnapshot;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private weeklyNiggleSnapshotService: WeeklyNiggleSnapshotService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWeeklyNiggleSnapshots();
    }

    load(id) {
        this.weeklyNiggleSnapshotService.find(id)
            .subscribe((weeklyNiggleSnapshotResponse: HttpResponse<WeeklyNiggleSnapshot>) => {
                this.weeklyNiggleSnapshot = weeklyNiggleSnapshotResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWeeklyNiggleSnapshots() {
        this.eventSubscriber = this.eventManager.subscribe(
            'weeklyNiggleSnapshotListModification',
            (response) => this.load(this.weeklyNiggleSnapshot.id)
        );
    }
}
