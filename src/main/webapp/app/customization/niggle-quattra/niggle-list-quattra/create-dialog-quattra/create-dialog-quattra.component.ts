import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NiggleService } from '../../../../entities/niggle/niggle.service';
import { Niggle, Priority } from '../../../../entities/niggle/niggle.model';
import { Plant , PlantService} from '../../../../entities/plant';
import { MaintenanceContractor, MaintenanceContractorService } from '../../../../entities/maintenance-contractor';

import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { MatDialogRef, MatSnackBar } from '@angular/material';

@Component({
  selector: 'jhi-create-dialog-quattra',
  templateUrl: './create-dialog-quattra.component.html',
  styleUrls: ['./create-dialog-quattra.component.css']
})
export class CreateDialogQuattraComponent implements OnInit {

  niggleForm: FormGroup;
  plants: Plant[];
  maintenancecontractors: MaintenanceContractor[];
  isSaving: boolean;
  contractor: MaintenanceContractor;

  constructor(
    public snackBar: MatSnackBar,
    private fb: FormBuilder,
    private jhiAlertService: JhiAlertService,
    private niggleService: NiggleService,
    private plantService: PlantService,
    private maintenanceContractorService: MaintenanceContractorService,
    public dialogRef: MatDialogRef<CreateDialogQuattraComponent>,
    private eventManager: JhiEventManager
  ) {
    this.createForm();
  }

  createForm() {
    this.niggleForm = this.fb.group({
      description: ['', Validators.required],
      status: 'OPEN',
      plant: ['', Validators.required],
      reference: '',
      comments: '',
      invoiceNo: ''
    });
  }

  ngOnInit() {
    this.plantService.query()
      .subscribe((res: HttpResponse<Plant[]>) => { this.plants = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    this.maintenanceContractorService.query()
      .subscribe((res: HttpResponse<MaintenanceContractor[]>) => {
        this.maintenancecontractors = res.body;
        this.contractor = this.maintenancecontractors.find((element) => {
          return element.name === 'Quattra';
        });
        this.niggleForm.patchValue({
          contractor: this.maintenancecontractors[0]
        });
      }, (res: HttpErrorResponse) => this.onError(res.message));

    this.onChanges();
  }

  onChanges() {
    this.niggleForm.get('status').valueChanges.subscribe((val) => {
      const referenceControl = this.niggleForm.get('reference');
      console.log(val);
      if (val === 'COMPLETED') {
        referenceControl.setValidators([Validators.required]);
      }else {
        referenceControl.clearValidators();
      }
      referenceControl.updateValueAndValidity();
    });
  }

  private onError(error: any) {
    this.jhiAlertService.error(error.message, null, null);
  }
  onSubmit() {
    this.isSaving = true;
    console.log('save!!');
    const newNiggle = this.prepareSaveNiggle();
    this.subscribeToSaveResponse(
      this.niggleService.create(newNiggle));

  }

  onCancel(): void {
    this.dialogRef.close();
  }

  prepareSaveNiggle(): Niggle {
    const formModel = this.niggleForm.value;

    const saveNiggle: Niggle = {
      description: formModel.description,
      plant: formModel.plant,
      assignedContractor: this.contractor,
      quattraComments: formModel.comments,
      priority: Priority.LOW,
      quattraReference: formModel.reference,
      status: formModel.status,
      invoiceNo: formModel.invoiceNo
    };
    return saveNiggle;
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<Niggle>>) {
    result.subscribe((res: HttpResponse<Niggle>) =>
      this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess(result: Niggle) {
    this.isSaving = false;
    this.eventManager.broadcast({ name: 'niggleListModification', content: 'OK' });
    this.snackBar.open('Niggle created',  'Dismiss', {
      duration: 3000
    });
    this.onCancel();
  }

  private onSaveError() {
    this.isSaving = false;
  }

}
