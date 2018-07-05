import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PrestartQuestionOption } from './prestart-question-option.model';
import { PrestartQuestionOptionPopupService } from './prestart-question-option-popup.service';
import { PrestartQuestionOptionService } from './prestart-question-option.service';

@Component({
    selector: 'jhi-prestart-question-option-delete-dialog',
    templateUrl: './prestart-question-option-delete-dialog.component.html'
})
export class PrestartQuestionOptionDeleteDialogComponent {

    prestartQuestionOption: PrestartQuestionOption;

    constructor(
        private prestartQuestionOptionService: PrestartQuestionOptionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.prestartQuestionOptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'prestartQuestionOptionListModification',
                content: 'Deleted an prestartQuestionOption'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-prestart-question-option-delete-popup',
    template: ''
})
export class PrestartQuestionOptionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prestartQuestionOptionPopupService: PrestartQuestionOptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.prestartQuestionOptionPopupService
                .open(PrestartQuestionOptionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
