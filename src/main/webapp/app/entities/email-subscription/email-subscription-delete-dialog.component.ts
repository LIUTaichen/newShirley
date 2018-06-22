import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmailSubscription } from './email-subscription.model';
import { EmailSubscriptionPopupService } from './email-subscription-popup.service';
import { EmailSubscriptionService } from './email-subscription.service';

@Component({
    selector: 'jhi-email-subscription-delete-dialog',
    templateUrl: './email-subscription-delete-dialog.component.html'
})
export class EmailSubscriptionDeleteDialogComponent {

    emailSubscription: EmailSubscription;

    constructor(
        private emailSubscriptionService: EmailSubscriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.emailSubscriptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'emailSubscriptionListModification',
                content: 'Deleted an emailSubscription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-email-subscription-delete-popup',
    template: ''
})
export class EmailSubscriptionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private emailSubscriptionPopupService: EmailSubscriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.emailSubscriptionPopupService
                .open(EmailSubscriptionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
