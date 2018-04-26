import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { MaintenanceContractor } from './maintenance-contractor.model';
import { MaintenanceContractorService } from './maintenance-contractor.service';

@Injectable()
export class MaintenanceContractorPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private maintenanceContractorService: MaintenanceContractorService

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
                this.maintenanceContractorService.find(id)
                    .subscribe((maintenanceContractorResponse: HttpResponse<MaintenanceContractor>) => {
                        const maintenanceContractor: MaintenanceContractor = maintenanceContractorResponse.body;
                        this.ngbModalRef = this.maintenanceContractorModalRef(component, maintenanceContractor);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.maintenanceContractorModalRef(component, new MaintenanceContractor());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    maintenanceContractorModalRef(component: Component, maintenanceContractor: MaintenanceContractor): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.maintenanceContractor = maintenanceContractor;
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
