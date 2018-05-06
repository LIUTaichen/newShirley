import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MaterialModule } from '../../material.module';

import { FleetManagementSharedModule } from '../../shared';
import {
    NiggleService,
    NigglePopupService,
    NiggleComponent,
    NiggleDetailComponent,
    NiggleDialogComponent,
    NigglePopupComponent,
    NiggleDeletePopupComponent,
    NiggleDeleteDialogComponent,
    niggleRoute,
    nigglePopupRoute,
} from './';
import { NiggleListDwComponent } from './niggle-list-dw/niggle-list-dw.component';
import { NiggleListQuattraComponent } from './niggle-list-quattra/niggle-list-quattra.component';
import { NiggleEditFormDwComponent } from './niggle-list-dw/niggle-edit-form-dw/niggle-edit-form-dw.component';
import { NiggleCreateFormQuattraComponent } from './niggle-list-quattra/niggle-create-form-quattra/niggle-create-form-quattra.component';
import { NiggleCreateDialogComponent } from './niggle-list-dw/niggle-create-dialog/niggle-create-dialog.component';
import { NiggleEditDialogComponent } from './niggle-list-dw/niggle-edit-dialog/niggle-edit-dialog.component';
import { NiggleDeleteDialogDwComponent } from './niggle-list-dw/niggle-delete-dialog-dw/niggle-delete-dialog-dw.component';

const ENTITY_STATES = [
    ...niggleRoute,
    ...nigglePopupRoute,
];

@NgModule({
    imports: [
        MaterialModule,
        ReactiveFormsModule,
        FleetManagementSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        NiggleComponent,
        NiggleDetailComponent,
        NiggleDialogComponent,
        NiggleDeleteDialogComponent,
        NigglePopupComponent,
        NiggleDeletePopupComponent,
        NiggleListDwComponent,
        NiggleListQuattraComponent,
        NiggleEditFormDwComponent,
        NiggleCreateFormQuattraComponent,
        NiggleCreateDialogComponent,
        NiggleEditDialogComponent,
        NiggleDeleteDialogDwComponent
    ],
    entryComponents: [
        NiggleComponent,
        NiggleDialogComponent,
        NigglePopupComponent,
        NiggleDeleteDialogComponent,
        NiggleDeletePopupComponent,
        NiggleEditDialogComponent,
        NiggleDeleteDialogDwComponent
    ],
    providers: [
        NiggleService,
        NigglePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FleetManagementNiggleModule {}
