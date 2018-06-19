import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmailSubscription } from './email-subscription.model';
import { EmailSubscriptionPopupService } from './email-subscription-popup.service';
import { EmailSubscriptionService } from './email-subscription.service';

@Component({
    selector: 'jhi-email-subscription-dialog',
    templateUrl: './email-subscription-dialog.component.html'
})
export class EmailSubscriptionDialogComponent implements OnInit {

    emailSubscription: EmailSubscription;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private emailSubscriptionService: EmailSubscriptionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.emailSubscription.id !== undefined) {
            this.subscribeToSaveResponse(
                this.emailSubscriptionService.update(this.emailSubscription));
        } else {
            this.subscribeToSaveResponse(
                this.emailSubscriptionService.create(this.emailSubscription));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EmailSubscription>>) {
        result.subscribe((res: HttpResponse<EmailSubscription>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: EmailSubscription) {
        this.eventManager.broadcast({ name: 'emailSubscriptionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-email-subscription-popup',
    template: ''
})
export class EmailSubscriptionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private emailSubscriptionPopupService: EmailSubscriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.emailSubscriptionPopupService
                    .open(EmailSubscriptionDialogComponent as Component, params['id']);
            } else {
                this.emailSubscriptionPopupService
                    .open(EmailSubscriptionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
