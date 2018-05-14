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
import { NiggleCreateDialogComponent } from './niggle-list-dw/niggle-create-dialog/niggle-create-dialog.component';
import { NiggleEditDialogComponent } from './niggle-list-dw/niggle-edit-dialog/niggle-edit-dialog.component';
import { NiggleDeleteDialogDwComponent } from './niggle-list-dw/niggle-delete-dialog-dw/niggle-delete-dialog-dw.component';
import { CreateDialogQuattraComponent } from './niggle-list-quattra/create-dialog-quattra/create-dialog-quattra.component';
import { EditDialogQuattraComponent } from './niggle-list-quattra/edit-dialog-quattra/edit-dialog-quattra.component';
import { DeleteDialogQuattraComponent } from './niggle-list-quattra/delete-dialog-quattra/delete-dialog-quattra.component';

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
        NiggleCreateDialogComponent,
        NiggleEditDialogComponent,
        NiggleDeleteDialogDwComponent,
        CreateDialogQuattraComponent,
        EditDialogQuattraComponent,
        DeleteDialogQuattraComponent
    ],
    entryComponents: [
        NiggleComponent,
        NiggleDialogComponent,
        NigglePopupComponent,
        NiggleDeleteDialogComponent,
        NiggleDeletePopupComponent,
        NiggleEditDialogComponent,
        NiggleDeleteDialogDwComponent,
        CreateDialogQuattraComponent,
        EditDialogQuattraComponent,
        DeleteDialogQuattraComponent
    ],
    providers: [
        NiggleService,
        NigglePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FleetManagementNiggleModule {}
