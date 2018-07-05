import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PrestartQuestion } from './prestart-question.model';
import { PrestartQuestionPopupService } from './prestart-question-popup.service';
import { PrestartQuestionService } from './prestart-question.service';

@Component({
    selector: 'jhi-prestart-question-delete-dialog',
    templateUrl: './prestart-question-delete-dialog.component.html'
})
export class PrestartQuestionDeleteDialogComponent {

    prestartQuestion: PrestartQuestion;

    constructor(
        private prestartQuestionService: PrestartQuestionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.prestartQuestionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'prestartQuestionListModification',
                content: 'Deleted an prestartQuestion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-prestart-question-delete-popup',
    template: ''
})
export class PrestartQuestionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prestartQuestionPopupService: PrestartQuestionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.prestartQuestionPopupService
                .open(PrestartQuestionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
