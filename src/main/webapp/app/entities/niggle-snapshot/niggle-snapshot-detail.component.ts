import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { NiggleSnapshot } from './niggle-snapshot.model';
import { NiggleSnapshotService } from './niggle-snapshot.service';

@Component({
    selector: 'jhi-niggle-snapshot-detail',
    templateUrl: './niggle-snapshot-detail.component.html'
})
export class NiggleSnapshotDetailComponent implements OnInit, OnDestroy {

    niggleSnapshot: NiggleSnapshot;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private niggleSnapshotService: NiggleSnapshotService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNiggleSnapshots();
    }

    load(id) {
        this.niggleSnapshotService.find(id)
            .subscribe((niggleSnapshotResponse: HttpResponse<NiggleSnapshot>) => {
                this.niggleSnapshot = niggleSnapshotResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNiggleSnapshots() {
        this.eventSubscriber = this.eventManager.subscribe(
            'niggleSnapshotListModification',
            (response) => this.load(this.niggleSnapshot.id)
        );
    }
}
