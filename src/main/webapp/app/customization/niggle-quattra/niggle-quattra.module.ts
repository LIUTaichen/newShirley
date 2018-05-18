import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MaterialModule } from '../../material.module';
import { FleetManagementSharedModule } from '../../shared';

import { niggleQuattraRoute } from './niggle-quattra.route';
import { CreateDialogQuattraComponent } from './niggle-list-quattra/create-dialog-quattra/create-dialog-quattra.component';
import { EditDialogQuattraComponent } from './niggle-list-quattra/edit-dialog-quattra/edit-dialog-quattra.component';
import { DeleteDialogQuattraComponent } from './niggle-list-quattra/delete-dialog-quattra/delete-dialog-quattra.component';
import { NiggleListQuattraComponent } from './niggle-list-quattra/niggle-list-quattra.component';

const ENTITY_STATES = [
  ...niggleQuattraRoute,
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
    NiggleListQuattraComponent,
    CreateDialogQuattraComponent,
    EditDialogQuattraComponent,
    DeleteDialogQuattraComponent
  ]
})
export class NiggleQuattraModule { }
