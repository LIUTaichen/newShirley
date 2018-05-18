import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MaterialModule } from '../../material.module';
import { FleetManagementSharedModule } from '../../shared';

import { niggleDwRoute } from './niggle-dw.route';
import { NiggleListDwComponent } from './niggle-list-dw/niggle-list-dw.component';
import { NiggleCreateDialogComponent } from './niggle-list-dw/niggle-create-dialog/niggle-create-dialog.component';
import { NiggleEditDialogComponent } from './niggle-list-dw/niggle-edit-dialog/niggle-edit-dialog.component';
import { NiggleDeleteDialogDwComponent } from './niggle-list-dw/niggle-delete-dialog-dw/niggle-delete-dialog-dw.component';

const ENTITY_STATES = [
  ...niggleDwRoute,
];

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FleetManagementSharedModule,
    RouterModule.forChild(ENTITY_STATES)
  ],
  declarations: [
    NiggleListDwComponent,
    NiggleCreateDialogComponent,
    NiggleEditDialogComponent,
    NiggleDeleteDialogDwComponent,
  ]
})
export class NiggleDwModule { }
