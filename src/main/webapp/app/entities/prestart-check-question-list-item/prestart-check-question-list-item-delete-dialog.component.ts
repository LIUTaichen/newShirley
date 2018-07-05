import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PrestartCheckQuestionListItem } from './prestart-check-question-list-item.model';
import { PrestartCheckQuestionListItemPopupService } from './prestart-check-question-list-item-popup.service';
import { PrestartCheckQuestionListItemService } from './prestart-check-question-list-item.service';

@Component({
    selector: 'jhi-prestart-check-question-list-item-delete-dialog',
    templateUrl: './prestart-check-question-list-item-delete-dialog.component.html'
})
export class PrestartCheckQuestionListItemDeleteDialogComponent {

    prestartCheckQuestionListItem: PrestartCheckQuestionListItem;

    constructor(
        private prestartCheckQuestionListItemService: PrestartCheckQuestionListItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.prestartCheckQuestionListItemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'prestartCheckQuestionListItemListModification',
                content: 'Deleted an prestartCheckQuestionListItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-prestart-check-question-list-item-delete-popup',
    template: ''
})
export class PrestartCheckQuestionListItemDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prestartCheckQuestionListItemPopupService: PrestartCheckQuestionListItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.prestartCheckQuestionListItemPopupService
                .open(PrestartCheckQuestionListItemDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
