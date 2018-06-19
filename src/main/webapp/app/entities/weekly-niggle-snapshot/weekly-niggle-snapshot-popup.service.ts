import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { WeeklyNiggleSnapshot } from './weekly-niggle-snapshot.model';
import { WeeklyNiggleSnapshotService } from './weekly-niggle-snapshot.service';

@Injectable()
export class WeeklyNiggleSnapshotPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private weeklyNiggleSnapshotService: WeeklyNiggleSnapshotService

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
                this.weeklyNiggleSnapshotService.find(id)
                    .subscribe((weeklyNiggleSnapshotResponse: HttpResponse<WeeklyNiggleSnapshot>) => {
                        const weeklyNiggleSnapshot: WeeklyNiggleSnapshot = weeklyNiggleSnapshotResponse.body;
                        if (weeklyNiggleSnapshot.weekEndingOn) {
                            weeklyNiggleSnapshot.weekEndingOn = {
                                year: weeklyNiggleSnapshot.weekEndingOn.getFullYear(),
                                month: weeklyNiggleSnapshot.weekEndingOn.getMonth() + 1,
                                day: weeklyNiggleSnapshot.weekEndingOn.getDate()
                            };
                        }
                        this.ngbModalRef = this.weeklyNiggleSnapshotModalRef(component, weeklyNiggleSnapshot);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.weeklyNiggleSnapshotModalRef(component, new WeeklyNiggleSnapshot());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    weeklyNiggleSnapshotModalRef(component: Component, weeklyNiggleSnapshot: WeeklyNiggleSnapshot): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.weeklyNiggleSnapshot = weeklyNiggleSnapshot;
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
