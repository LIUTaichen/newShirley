import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PrestartCheckResponse } from './prestart-check-response.model';
import { PrestartCheckResponseService } from './prestart-check-response.service';

@Injectable()
export class PrestartCheckResponsePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private prestartCheckResponseService: PrestartCheckResponseService

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
                this.prestartCheckResponseService.find(id)
                    .subscribe((prestartCheckResponseResponse: HttpResponse<PrestartCheckResponse>) => {
                        const prestartCheckResponse: PrestartCheckResponse = prestartCheckResponseResponse.body;
                        this.ngbModalRef = this.prestartCheckResponseModalRef(component, prestartCheckResponse);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.prestartCheckResponseModalRef(component, new PrestartCheckResponse());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    prestartCheckResponseModalRef(component: Component, prestartCheckResponse: PrestartCheckResponse): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.prestartCheckResponse = prestartCheckResponse;
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
