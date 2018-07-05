import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FleetManagementSharedModule } from '../../shared';
import {
    PrestartCheckService,
    PrestartCheckPopupService,
    PrestartCheckComponent,
    PrestartCheckDetailComponent,
    PrestartCheckDialogComponent,
    PrestartCheckPopupComponent,
    PrestartCheckDeletePopupComponent,
    PrestartCheckDeleteDialogComponent,
    prestartCheckRoute,
    prestartCheckPopupRoute,
} from './';

const ENTITY_STATES = [
    ...prestartCheckRoute,
    ...prestartCheckPopupRoute,
];

@NgModule({
    imports: [
        FleetManagementSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PrestartCheckComponent,
        PrestartCheckDetailComponent,
        PrestartCheckDialogComponent,
        PrestartCheckDeleteDialogComponent,
        PrestartCheckPopupComponent,
        PrestartCheckDeletePopupComponent,
    ],
    entryComponents: [
        PrestartCheckComponent,
        PrestartCheckDialogComponent,
        PrestartCheckPopupComponent,
        PrestartCheckDeleteDialogComponent,
        PrestartCheckDeletePopupComponent,
    ],
    providers: [
        PrestartCheckService,
        PrestartCheckPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FleetManagementPrestartCheckModule {}
