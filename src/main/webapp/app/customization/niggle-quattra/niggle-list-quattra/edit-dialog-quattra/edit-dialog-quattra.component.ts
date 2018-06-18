import { Component, OnInit, Inject } from '@angular/core';
import { NiggleService } from '../../../../entities/niggle/niggle.service';
import { Niggle } from '../../../../entities/niggle/niggle.model';
import { Plant, PlantService } from '../../../../entities/plant';
import { MaintenanceContractor, MaintenanceContractorService } from '../../../../entities/maintenance-contractor';
import {FormControl, FormGroupDirective, NgForm, Validators, FormBuilder, FormGroup} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';

import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { MatDialogRef, MatSnackBar, MAT_DIALOG_DATA } from '@angular/material';
import { map } from 'rxjs/operators/map';

@Component({
  selector: 'jhi-edit-dialog-quattra',
  templateUrl: './edit-dialog-quattra.component.html',
  styleUrls: ['./edit-dialog-quattra.component.css']
})
export class EditDialogQuattraComponent implements OnInit {

  niggle: Niggle;
  niggleForm: FormGroup;
  maintenancecontractors: MaintenanceContractor[];
  isSaving: boolean;
  matcher = new MyErrorStateMatcher();

  constructor(
    public snackBar: MatSnackBar,
    private fb: FormBuilder,
    private jhiAlertService: JhiAlertService,
    private niggleService: NiggleService,
    private plantService: PlantService,
    public dialogRef: MatDialogRef<EditDialogQuattraComponent>,
    private eventManager: JhiEventManager,
    @Inject(MAT_DIALOG_DATA) public data: any) {
    this.niggle = data.niggle;
  }

  createForm() {

    this.niggleForm = this.fb.group({
      description: [{ value: '', disabled: true }, Validators.required],
      status: '',
      plant: [{ value: '', disabled: true }, Validators.required],
      reference: '',
      comments: '',
      invoiceNo: '',
      priority: { value: '', disabled: true },
      eta: ''
    });
  }

  ngOnInit() {
    this.createForm();
    this.niggleForm.controls.status.valueChanges.subscribe(
      (val) => {
        console.log('responding to status changes', val);
        if (val === 'ON_HOLD') {
          this.niggleForm.controls.comments.setValidators(Validators.required);
          this.niggleForm.controls.eta.setValidators(Validators.required);
          this.niggleForm.controls.comments.updateValueAndValidity();
          this.niggleForm.controls.eta.updateValueAndValidity();
        } else {
          this.niggleForm.controls.comments.setValidators([]);
          this.niggleForm.controls.eta.setValidators([]);
          this.niggleForm.controls.comments.updateValueAndValidity();
          this.niggleForm.controls.eta.updateValueAndValidity();
        }
      }
    );
    this.setOriginalValue();
  }

  private onError(error: any) {
    this.jhiAlertService.error(error.message, null, null);
    this.snackBar.open('Niggle update failed', 'Dismiss', {
      duration: 3000
    });
  }
  onSubmit() {
    this.isSaving = true;
    console.log('save!!');
    this.prepareSaveNiggle();
    this.subscribeToSaveResponse(
      this.niggleService.update(this.niggle));

  }

  onCancel(): void {
    this.dialogRef.close();
  }

  prepareSaveNiggle() {
    const formModel = this.niggleForm.value;
    this.niggle.status = formModel.status;
    this.niggle.quattraReference = formModel.reference;
    this.niggle.invoiceNo = formModel.invoiceNo;
    this.niggle.quattraComments = formModel.comments;
    this.niggle.eta = formModel.eta;
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<Niggle>>) {
    result.subscribe((res: HttpResponse<Niggle>) =>
      this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess(result: Niggle) {
    this.isSaving = false;
    this.eventManager.broadcast({ name: 'niggleListModification', content: 'OK' });
    this.snackBar.open('Niggle updated', 'Dismiss', {
      duration: 3000
    });
    this.onCancel();
  }

  private onSaveError() {
    this.isSaving = false;
    this.snackBar.open('Niggle updated', 'Dismiss', {
      duration: 3000
    });
  }

  setOriginalValue() {
    this.niggleForm.setValue({
      description: this.niggle.description,
      plant: this.niggle.plant['fleetId'],
      status: this.niggle.status,
      priority: this.niggle.priority,
      reference: this.niggle.quattraReference,
      comments: this.niggle.quattraComments,
      invoiceNo: this.niggle.invoiceNo,
      eta: this.niggle.eta
    });
  }

  compareObjects(o1: any, o2: any): boolean {
    return o1.id === o2.id;
  }
}

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return (control && control.invalid );
  }
}
