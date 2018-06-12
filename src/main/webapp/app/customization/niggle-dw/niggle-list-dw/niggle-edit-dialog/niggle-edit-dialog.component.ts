import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NiggleService } from '../../../../entities/niggle/niggle.service';
import { Niggle } from '../../../../entities/niggle/niggle.model';
import { Plant } from '../../../../entities/plant';
import { MaintenanceContractor } from '../../../../entities/maintenance-contractor';
import { NiggleDeleteDialogDwComponent } from '../niggle-delete-dialog-dw/niggle-delete-dialog-dw.component';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';
import { MatDialogRef, MatSnackBar, MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { startWith } from 'rxjs/operators/startWith';
import { map } from 'rxjs/operators/map';
import { NiggleRow } from '../niggle-row.model';

@Component({
  selector: 'jhi-niggle-edit-dialog',
  templateUrl: './niggle-edit-dialog.component.html',
  styleUrls: ['./niggle-edit-dialog.component.css']
})
export class NiggleEditDialogComponent implements OnInit {

  niggle: Niggle;
  niggleForm: FormGroup;
  plants: Plant[];
  niggleRow: NiggleRow;
  maintenanceContractors: MaintenanceContractor[];
  isSaving: boolean;
  filteredOptions: Observable<Plant[]>;

  constructor(
    public snackBar: MatSnackBar,
    private fb: FormBuilder,
    private niggleService: NiggleService,
    public dialogRef: MatDialogRef<NiggleEditDialogComponent>,
    private eventManager: JhiEventManager,
    public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any) {
    this.niggle = data.niggle;
    this.plants = data.plants;
    this.niggleRow = data.row;
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
    this.setOriginalValue();
  }

  createForm() {

    this.niggleForm = this.fb.group({
      description: [this.niggle.description, Validators.required],
      status: this.niggle.status,
      priority: this.niggle.priority,
      plant: [this.niggle.plant, Validators.required],
      contractor: this.niggle.assignedContractor,
      note: this.niggle.note
    });
  }

  ngOnInit() {

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

    this.niggle.description = formModel.description;
    this.niggle.plant = formModel.plant;
    this.niggle.assignedContractor = formModel.contractor;
    this.niggle.note = formModel.note;
    this.niggle.priority = formModel.priority;
    this.niggle.status = formModel.status;
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
      contractor: this.niggle.assignedContractor,
      note: this.niggle.note,
      priority: this.niggle.priority,
      status: this.niggle.status
    });
  }

  compareObjects(o1: any, o2: any): boolean {
    return o1.id === o2.id;
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

  openDeleteDialog(id: number): void {
    this.dialogRef.close();
    const dialogRef = this.dialog.open(NiggleDeleteDialogDwComponent, {
      panelClass: 'niggle-delete-panel',
      data: { id }
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
    });
  }

}
