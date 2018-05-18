import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

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

const ENTITY_STATES = [
    ...niggleRoute,
    ...nigglePopupRoute,
];

@NgModule({
    imports: [
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
    ],
    entryComponents: [
        NiggleComponent,
        NiggleDialogComponent,
        NigglePopupComponent,
        NiggleDeleteDialogComponent,
        NiggleDeletePopupComponent,
    ],
    providers: [
        NiggleService,
        NigglePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FleetManagementNiggleModule {}
