import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { EmailSubscription } from './email-subscription.model';
import { EmailSubscriptionService } from './email-subscription.service';

@Injectable()
export class EmailSubscriptionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private emailSubscriptionService: EmailSubscriptionService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.emailSubscriptionService.find(id)
                    .subscribe((emailSubscriptionResponse: HttpResponse<EmailSubscription>) => {
                        const emailSubscription: EmailSubscription = emailSubscriptionResponse.body;
                        this.ngbModalRef = this.emailSubscriptionModalRef(component, emailSubscription);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.emailSubscriptionModalRef(component, new EmailSubscription());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    emailSubscriptionModalRef(component: Component, emailSubscription: EmailSubscription): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.emailSubscription = emailSubscription;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
