import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PrestartCheckConfig } from './prestart-check-config.model';
import { PrestartCheckConfigService } from './prestart-check-config.service';

@Injectable()
export class PrestartCheckConfigPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private prestartCheckConfigService: PrestartCheckConfigService

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
                this.prestartCheckConfigService.find(id)
                    .subscribe((prestartCheckConfigResponse: HttpResponse<PrestartCheckConfig>) => {
                        const prestartCheckConfig: PrestartCheckConfig = prestartCheckConfigResponse.body;
                        this.ngbModalRef = this.prestartCheckConfigModalRef(component, prestartCheckConfig);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.prestartCheckConfigModalRef(component, new PrestartCheckConfig());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    prestartCheckConfigModalRef(component: Component, prestartCheckConfig: PrestartCheckConfig): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.prestartCheckConfig = prestartCheckConfig;
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
