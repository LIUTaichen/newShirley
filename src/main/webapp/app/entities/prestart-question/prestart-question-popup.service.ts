import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PrestartQuestion } from './prestart-question.model';
import { PrestartQuestionService } from './prestart-question.service';

@Injectable()
export class PrestartQuestionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private prestartQuestionService: PrestartQuestionService

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
                this.prestartQuestionService.find(id)
                    .subscribe((prestartQuestionResponse: HttpResponse<PrestartQuestion>) => {
                        const prestartQuestion: PrestartQuestion = prestartQuestionResponse.body;
                        this.ngbModalRef = this.prestartQuestionModalRef(component, prestartQuestion);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.prestartQuestionModalRef(component, new PrestartQuestion());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    prestartQuestionModalRef(component: Component, prestartQuestion: PrestartQuestion): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.prestartQuestion = prestartQuestion;
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
