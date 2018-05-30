import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { OffHireRequest } from './off-hire-request.model';
import { OffHireRequestService } from './off-hire-request.service';

@Injectable()
export class OffHireRequestPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private offHireRequestService: OffHireRequestService

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
                this.offHireRequestService.find(id)
                    .subscribe((offHireRequestResponse: HttpResponse<OffHireRequest>) => {
                        const offHireRequest: OffHireRequest = offHireRequestResponse.body;
                        this.ngbModalRef = this.offHireRequestModalRef(component, offHireRequest);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.offHireRequestModalRef(component, new OffHireRequest());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    offHireRequestModalRef(component: Component, offHireRequest: OffHireRequest): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.offHireRequest = offHireRequest;
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
