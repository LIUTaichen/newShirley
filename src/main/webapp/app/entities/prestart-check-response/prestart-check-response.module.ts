import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FleetManagementSharedModule } from '../../shared';
import {
    PrestartCheckResponseService,
    PrestartCheckResponsePopupService,
    PrestartCheckResponseComponent,
    PrestartCheckResponseDetailComponent,
    PrestartCheckResponseDialogComponent,
    PrestartCheckResponsePopupComponent,
    PrestartCheckResponseDeletePopupComponent,
    PrestartCheckResponseDeleteDialogComponent,
    prestartCheckResponseRoute,
    prestartCheckResponsePopupRoute,
} from './';

const ENTITY_STATES = [
    ...prestartCheckResponseRoute,
    ...prestartCheckResponsePopupRoute,
];

@NgModule({
    imports: [
        FleetManagementSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PrestartCheckResponseComponent,
        PrestartCheckResponseDetailComponent,
        PrestartCheckResponseDialogComponent,
        PrestartCheckResponseDeleteDialogComponent,
        PrestartCheckResponsePopupComponent,
        PrestartCheckResponseDeletePopupComponent,
    ],
    entryComponents: [
        PrestartCheckResponseComponent,
        PrestartCheckResponseDialogComponent,
        PrestartCheckResponsePopupComponent,
        PrestartCheckResponseDeleteDialogComponent,
        PrestartCheckResponseDeletePopupComponent,
    ],
    providers: [
        PrestartCheckResponseService,
        PrestartCheckResponsePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FleetManagementPrestartCheckResponseModule {}
