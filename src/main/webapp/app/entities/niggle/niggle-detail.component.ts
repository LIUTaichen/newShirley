import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Niggle } from './niggle.model';
import { NiggleService } from './niggle.service';

@Component({
    selector: 'jhi-niggle-detail',
    templateUrl: './niggle-detail.component.html'
})
export class NiggleDetailComponent implements OnInit, OnDestroy {

    niggle: Niggle;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private niggleService: NiggleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNiggles();
    }

    load(id) {
        this.niggleService.find(id)
            .subscribe((niggleResponse: HttpResponse<Niggle>) => {
                this.niggle = niggleResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNiggles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'niggleListModification',
            (response) => this.load(this.niggle.id)
        );
    }
}
