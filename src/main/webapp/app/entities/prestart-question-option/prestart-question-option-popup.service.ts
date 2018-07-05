import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PrestartQuestionOption } from './prestart-question-option.model';
import { PrestartQuestionOptionService } from './prestart-question-option.service';

@Injectable()
export class PrestartQuestionOptionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private prestartQuestionOptionService: PrestartQuestionOptionService

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
                this.prestartQuestionOptionService.find(id)
                    .subscribe((prestartQuestionOptionResponse: HttpResponse<PrestartQuestionOption>) => {
                        const prestartQuestionOption: PrestartQuestionOption = prestartQuestionOptionResponse.body;
                        this.ngbModalRef = this.prestartQuestionOptionModalRef(component, prestartQuestionOption);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.prestartQuestionOptionModalRef(component, new PrestartQuestionOption());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    prestartQuestionOptionModalRef(component: Component, prestartQuestionOption: PrestartQuestionOption): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.prestartQuestionOption = prestartQuestionOption;
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
