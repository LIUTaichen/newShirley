import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { NiggleSnapshot } from './niggle-snapshot.model';
import { NiggleSnapshotService } from './niggle-snapshot.service';

@Injectable()
export class NiggleSnapshotPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private niggleSnapshotService: NiggleSnapshotService

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
                this.niggleSnapshotService.find(id)
                    .subscribe((niggleSnapshotResponse: HttpResponse<NiggleSnapshot>) => {
                        const niggleSnapshot: NiggleSnapshot = niggleSnapshotResponse.body;
                        if (niggleSnapshot.date) {
                            niggleSnapshot.date = {
                                year: niggleSnapshot.date.getFullYear(),
                                month: niggleSnapshot.date.getMonth() + 1,
                                day: niggleSnapshot.date.getDate()
                            };
                        }
                        this.ngbModalRef = this.niggleSnapshotModalRef(component, niggleSnapshot);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.niggleSnapshotModalRef(component, new NiggleSnapshot());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    niggleSnapshotModalRef(component: Component, niggleSnapshot: NiggleSnapshot): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.niggleSnapshot = niggleSnapshot;
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
