import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NiggleService } from '../../../../entities/niggle/niggle.service';
import { Niggle } from '../../../../entities/niggle/niggle.model';
import { Plant , PlantService} from '../../../../entities/plant';
import { MaintenanceContractor, MaintenanceContractorService } from '../../../../entities/maintenance-contractor';

import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { MatDialogRef, MatSnackBar, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'jhi-edit-dialog-quattra',
  templateUrl: './edit-dialog-quattra.component.html',
  styleUrls: ['./edit-dialog-quattra.component.css']
})
export class EditDialogQuattraComponent implements OnInit {

  niggle: Niggle;
  niggleForm: FormGroup;
  plants: Plant[];
  maintenancecontractors: MaintenanceContractor[];
  isSaving: boolean;

  constructor(
    public snackBar: MatSnackBar,
    private fb: FormBuilder,
    private jhiAlertService: JhiAlertService,
    private niggleService: NiggleService,
    private plantService: PlantService,
    private maintenanceContractorService: MaintenanceContractorService,
    public dialogRef: MatDialogRef<EditDialogQuattraComponent>,
    private eventManager: JhiEventManager,
    @Inject(MAT_DIALOG_DATA) public data: any) {
    this.niggle = data.niggle;
    this.createForm();
    this.setOriginalValue();
  }

  createForm() {

    this.niggleForm = this.fb.group({
      description: [{value: '',  disabled: true}, Validators.required],
      status: '',
      plant: [{value: '',  disabled: true}, Validators.required],
      reference: '',
      comments: '',
      invoiceNo: '',
      priority: {value: '', disabled: true}
    });
  }

  ngOnInit() {
    this.plantService.query()
      .subscribe((res: HttpResponse<Plant[]>) => { this.plants = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    this.maintenanceContractorService.query()
      .subscribe((res: HttpResponse<MaintenanceContractor[]>) => {
        this.maintenancecontractors = res.body;
        this.niggleForm.patchValue({
          contractor: this.maintenancecontractors[0]
        });
      }, (res: HttpErrorResponse) => this.onError(res.message));
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
      plant: this.niggle.plant,
      status: this.niggle.status,
      priority: this.niggle.priority,
      reference: this.niggle.quattraReference,
      comments: this.niggle.quattraComments,
      invoiceNo: this.niggle.invoiceNo
    });
  }

  compareObjects(o1: any, o2: any): boolean {
    return o1.id === o2.id;
  }
}
