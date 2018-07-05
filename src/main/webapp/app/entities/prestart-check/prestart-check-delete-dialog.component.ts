import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PrestartCheck } from './prestart-check.model';
import { PrestartCheckPopupService } from './prestart-check-popup.service';
import { PrestartCheckService } from './prestart-check.service';

@Component({
    selector: 'jhi-prestart-check-delete-dialog',
    templateUrl: './prestart-check-delete-dialog.component.html'
})
export class PrestartCheckDeleteDialogComponent {

    prestartCheck: PrestartCheck;

    constructor(
        private prestartCheckService: PrestartCheckService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.prestartCheckService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'prestartCheckListModification',
                content: 'Deleted an prestartCheck'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-prestart-check-delete-popup',
    template: ''
})
export class PrestartCheckDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prestartCheckPopupService: PrestartCheckPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.prestartCheckPopupService
                .open(PrestartCheckDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
