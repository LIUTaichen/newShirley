import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PrestartCheckQuestionListItem } from './prestart-check-question-list-item.model';
import { PrestartCheckQuestionListItemService } from './prestart-check-question-list-item.service';

@Injectable()
export class PrestartCheckQuestionListItemPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private prestartCheckQuestionListItemService: PrestartCheckQuestionListItemService

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
                this.prestartCheckQuestionListItemService.find(id)
                    .subscribe((prestartCheckQuestionListItemResponse: HttpResponse<PrestartCheckQuestionListItem>) => {
                        const prestartCheckQuestionListItem: PrestartCheckQuestionListItem = prestartCheckQuestionListItemResponse.body;
                        this.ngbModalRef = this.prestartCheckQuestionListItemModalRef(component, prestartCheckQuestionListItem);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.prestartCheckQuestionListItemModalRef(component, new PrestartCheckQuestionListItem());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    prestartCheckQuestionListItemModalRef(component: Component, prestartCheckQuestionListItem: PrestartCheckQuestionListItem): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.prestartCheckQuestionListItem = prestartCheckQuestionListItem;
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
