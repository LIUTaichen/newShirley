import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FleetManagementSharedModule } from '../../shared';
import {
    PrestartCheckConfigService,
    PrestartCheckConfigPopupService,
    PrestartCheckConfigComponent,
    PrestartCheckConfigDetailComponent,
    PrestartCheckConfigDialogComponent,
    PrestartCheckConfigPopupComponent,
    PrestartCheckConfigDeletePopupComponent,
    PrestartCheckConfigDeleteDialogComponent,
    prestartCheckConfigRoute,
    prestartCheckConfigPopupRoute,
} from './';

const ENTITY_STATES = [
    ...prestartCheckConfigRoute,
    ...prestartCheckConfigPopupRoute,
];

@NgModule({
    imports: [
        FleetManagementSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PrestartCheckConfigComponent,
        PrestartCheckConfigDetailComponent,
        PrestartCheckConfigDialogComponent,
        PrestartCheckConfigDeleteDialogComponent,
        PrestartCheckConfigPopupComponent,
        PrestartCheckConfigDeletePopupComponent,
    ],
    entryComponents: [
        PrestartCheckConfigComponent,
        PrestartCheckConfigDialogComponent,
        PrestartCheckConfigPopupComponent,
        PrestartCheckConfigDeleteDialogComponent,
        PrestartCheckConfigDeletePopupComponent,
    ],
    providers: [
        PrestartCheckConfigService,
        PrestartCheckConfigPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FleetManagementPrestartCheckConfigModule {}
