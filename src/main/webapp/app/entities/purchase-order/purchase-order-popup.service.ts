import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PurchaseOrder } from './purchase-order.model';
import { PurchaseOrderService } from './purchase-order.service';

@Injectable()
export class PurchaseOrderPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private purchaseOrderService: PurchaseOrderService

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
                this.purchaseOrderService.find(id)
                    .subscribe((purchaseOrderResponse: HttpResponse<PurchaseOrder>) => {
                        const purchaseOrder: PurchaseOrder = purchaseOrderResponse.body;
                        this.ngbModalRef = this.purchaseOrderModalRef(component, purchaseOrder);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.purchaseOrderModalRef(component, new PurchaseOrder());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    purchaseOrderModalRef(component: Component, purchaseOrder: PurchaseOrder): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.purchaseOrder = purchaseOrder;
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
