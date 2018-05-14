import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Niggle } from './niggle.model';
import { NiggleService } from './niggle.service';

@Injectable()
export class NigglePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private niggleService: NiggleService

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
                this.niggleService.find(id)
                    .subscribe((niggleResponse: HttpResponse<Niggle>) => {
                        const niggle: Niggle = niggleResponse.body;
                        niggle.dateOpened = this.datePipe
                            .transform(niggle.dateOpened, 'yyyy-MM-ddTHH:mm:ss');
                        niggle.dateClosed = this.datePipe
                            .transform(niggle.dateClosed, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.niggleModalRef(component, niggle);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.niggleModalRef(component, new Niggle());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    niggleModalRef(component: Component, niggle: Niggle): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.niggle = niggle;
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
