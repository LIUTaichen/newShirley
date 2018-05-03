import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Niggle } from '../../niggle.model';
import { NiggleService } from '../../niggle.service';
import { Plant, PlantService } from '../../../plant';
import { MaintenanceContractor, MaintenanceContractorService } from '../../../maintenance-contractor';

import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-niggle-create-dialog',
  templateUrl: './niggle-create-dialog.component.html',
  styleUrls: ['./niggle-create-dialog.component.css']
})
export class NiggleCreateDialogComponent implements OnInit {

  niggleForm: FormGroup;
  plants: Plant[];
  maintenancecontractors: MaintenanceContractor[];

  constructor(
    private fb: FormBuilder,
    private jhiAlertService: JhiAlertService,
    private niggleService: NiggleService,
    private plantService: PlantService,
    private maintenanceContractorService: MaintenanceContractorService
  ) {
    this.createForm();
  }

  createForm() {
    this.niggleForm = this.fb.group({
      description: ['', Validators.required],
      priority:  '',
      plant:  ['', Validators.required],
      contractor : '',
      note: ''
    });
  }

  ngOnInit() {
    this.plantService.query()
            .subscribe((res: HttpResponse<Plant[]>) => { this.plants = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    this.maintenanceContractorService.query()
        .subscribe((res: HttpResponse<MaintenanceContractor[]>) => { this.maintenancecontractors = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
  }

  private onError(error: any) {
    this.jhiAlertService.error(error.message, null, null);
}

}
