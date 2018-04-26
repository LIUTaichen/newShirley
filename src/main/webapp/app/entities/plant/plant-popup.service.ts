import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Plant } from './plant.model';
import { PlantService } from './plant.service';

@Injectable()
export class PlantPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private plantService: PlantService

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
                this.plantService.find(id)
                    .subscribe((plantResponse: HttpResponse<Plant>) => {
                        const plant: Plant = plantResponse.body;
                        plant.purchaseDate = this.datePipe
                            .transform(plant.purchaseDate, 'yyyy-MM-ddTHH:mm:ss');
                        plant.dateOfManufacture = this.datePipe
                            .transform(plant.dateOfManufacture, 'yyyy-MM-ddTHH:mm:ss');
                        plant.certificateDueDate = this.datePipe
                            .transform(plant.certificateDueDate, 'yyyy-MM-ddTHH:mm:ss');
                        plant.registrationDueDate = this.datePipe
                            .transform(plant.registrationDueDate, 'yyyy-MM-ddTHH:mm:ss');
                        plant.lastLocationUpdateTime = this.datePipe
                            .transform(plant.lastLocationUpdateTime, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.plantModalRef(component, plant);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.plantModalRef(component, new Plant());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    plantModalRef(component: Component, plant: Plant): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.plant = plant;
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
