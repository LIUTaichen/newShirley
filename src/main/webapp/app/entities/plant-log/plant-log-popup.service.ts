import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { PlantLog } from './plant-log.model';
import { PlantLogService } from './plant-log.service';

@Injectable()
export class PlantLogPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private plantLogService: PlantLogService

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
                this.plantLogService.find(id)
                    .subscribe((plantLogResponse: HttpResponse<PlantLog>) => {
                        const plantLog: PlantLog = plantLogResponse.body;
                        plantLog.wofDueDate = this.datePipe
                            .transform(plantLog.wofDueDate, 'yyyy-MM-ddTHH:mm:ss');
                        plantLog.cofDueDate = this.datePipe
                            .transform(plantLog.cofDueDate, 'yyyy-MM-ddTHH:mm:ss');
                        plantLog.serviceDueDate = this.datePipe
                            .transform(plantLog.serviceDueDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.plantLogModalRef(component, plantLog);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.plantLogModalRef(component, new PlantLog());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    plantLogModalRef(component: Component, plantLog: PlantLog): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.plantLog = plantLog;
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
