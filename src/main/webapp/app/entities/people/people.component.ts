import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { People } from './people.model';
import { PeopleService } from './people.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-people',
    templateUrl: './people.component.html'
})
export class PeopleComponent implements OnInit, OnDestroy {
people: People[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private peopleService: PeopleService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.peopleService.query().subscribe(
            (res: HttpResponse<People[]>) => {
                this.people = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPeople();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: People) {
        return item.id;
    }
    registerChangeInPeople() {
        this.eventSubscriber = this.eventManager.subscribe('peopleListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
