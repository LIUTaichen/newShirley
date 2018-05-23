import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Niggle } from '../../../../entities/niggle/niggle.model';
import { NiggleService } from '../../../../entities/niggle/niggle.service';
import { Plant } from '../../../../entities/plant';
import { MaintenanceContractor } from '../../../../entities/maintenance-contractor';

import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';
import { MatDialogRef, MatSnackBar, MAT_DIALOG_DATA } from '@angular/material';
import { startWith } from 'rxjs/operators/startWith';
import { map } from 'rxjs/operators/map';

@Component({
  selector: 'jhi-niggle-create-dialog',
  templateUrl: './niggle-create-dialog.component.html',
  styleUrls: ['./niggle-create-dialog.component.css']
})
export class NiggleCreateDialogComponent implements OnInit {

  niggleForm: FormGroup;
  plants: Plant[];
  maintenanceContractors: MaintenanceContractor[];
  isSaving: boolean;
  filteredOptions: Observable<Plant[]>;

  constructor(
    public snackBar: MatSnackBar,
    private fb: FormBuilder,
    private niggleService: NiggleService,
    public dialogRef: MatDialogRef<NiggleCreateDialogComponent>,
    private eventManager: JhiEventManager,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.plants = data.plants;
    this.maintenanceContractors = data.maintenanceContractors;
    this.createForm();
    this.filteredOptions = this.niggleForm.controls.plant.valueChanges
      .pipe(
        startWith(''),
        map((val) => {
          const filtered = this.filter(val);
          console.log(filtered.length);
          return filtered;
        }
        )
      );
  }

  createForm() {
    this.niggleForm = this.fb.group({
      description: ['', Validators.required],
      status: 'SUBMITTED',
      priority: 'LOW',
      plant: ['', Validators.required],
      contractor: this.maintenanceContractors[0],
      note: ''
    });
  }

  ngOnInit() {
    // TODO: remove comments
    // this.plantService.query()
    //   .subscribe((res: HttpResponse<Plant[]>) => { this.plants = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    // this.maintenanceContractorService.query()
    //   .subscribe((res: HttpResponse<MaintenanceContractor[]>) => {
    //     this.maintenancecontractors = res.body;
    //     this.niggleForm.patchValue({
    //       contractor: this.maintenancecontractors[0]
    //     });
    //   }, (res: HttpErrorResponse) => this.onError(res.message));
  }

  // private onError(error: any) {
  //   this.jhiAlertService.error(error.message, null, null);
  // }
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
      assignedContractor: formModel.contractor,
      note: formModel.note,
      priority: formModel.priority,
      status: formModel.status
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
    this.snackBar.open('Niggle created', 'Dismiss', {
      duration: 3000
    });
    this.onCancel();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  filter(val: string): Plant[] {
    console.log('filtering with', val);
    let feed: string;
    if (val === null) {
      feed = '';
    } else {
      feed = val.toString().toLocaleLowerCase();
    }
    return this.plants.filter((option) =>
      option.fleetId.toLowerCase().indexOf(feed) === 0
    );
  }

  displayPlant(plant?: Plant): string | undefined {
    return plant ? plant.fleetId : undefined;
  }

  handleLowerCaseInput() {
    let feed: string;
    if (this.niggleForm.value.plant && !this.niggleForm.value.plant.id) {
      feed = this.niggleForm.value.plant.toLocaleLowerCase();
      console.log('looking for a match with feed: ', feed);
      for (let i = 0; i < this.plants.length; i++) {
        const option = this.plants[i];
        if (option.fleetId.toLowerCase() === feed) {
          console.log('found matching record: ', option);
          this.niggleForm.controls.plant.setValue(option);
        }
      }

    }
  }

}
