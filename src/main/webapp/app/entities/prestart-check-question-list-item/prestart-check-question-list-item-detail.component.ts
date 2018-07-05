import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PrestartCheckQuestionListItem } from './prestart-check-question-list-item.model';
import { PrestartCheckQuestionListItemService } from './prestart-check-question-list-item.service';

@Component({
    selector: 'jhi-prestart-check-question-list-item-detail',
    templateUrl: './prestart-check-question-list-item-detail.component.html'
})
export class PrestartCheckQuestionListItemDetailComponent implements OnInit, OnDestroy {

    prestartCheckQuestionListItem: PrestartCheckQuestionListItem;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private prestartCheckQuestionListItemService: PrestartCheckQuestionListItemService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPrestartCheckQuestionListItems();
    }

    load(id) {
        this.prestartCheckQuestionListItemService.find(id)
            .subscribe((prestartCheckQuestionListItemResponse: HttpResponse<PrestartCheckQuestionListItem>) => {
                this.prestartCheckQuestionListItem = prestartCheckQuestionListItemResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPrestartCheckQuestionListItems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'prestartCheckQuestionListItemListModification',
            (response) => this.load(this.prestartCheckQuestionListItem.id)
        );
    }
}
