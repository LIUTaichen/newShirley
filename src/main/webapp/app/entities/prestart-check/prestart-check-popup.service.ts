import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PrestartCheck } from './prestart-check.model';
import { PrestartCheckService } from './prestart-check.service';

@Injectable()
export class PrestartCheckPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private prestartCheckService: PrestartCheckService

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
                this.prestartCheckService.find(id)
                    .subscribe((prestartCheckResponse: HttpResponse<PrestartCheck>) => {
                        const prestartCheck: PrestartCheck = prestartCheckResponse.body;
                        this.ngbModalRef = this.prestartCheckModalRef(component, prestartCheck);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.prestartCheckModalRef(component, new PrestartCheck());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    prestartCheckModalRef(component: Component, prestartCheck: PrestartCheck): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.prestartCheck = prestartCheck;
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
