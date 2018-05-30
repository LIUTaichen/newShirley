import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FleetManagementSharedModule } from '../../shared';
import {
    OffHireRequestService,
    OffHireRequestPopupService,
    OffHireRequestComponent,
    OffHireRequestDetailComponent,
    OffHireRequestDialogComponent,
    OffHireRequestPopupComponent,
    OffHireRequestDeletePopupComponent,
    OffHireRequestDeleteDialogComponent,
    offHireRequestRoute,
    offHireRequestPopupRoute,
} from './';

const ENTITY_STATES = [
    ...offHireRequestRoute,
    ...offHireRequestPopupRoute,
];

@NgModule({
    imports: [
        FleetManagementSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OffHireRequestComponent,
        OffHireRequestDetailComponent,
        OffHireRequestDialogComponent,
        OffHireRequestDeleteDialogComponent,
        OffHireRequestPopupComponent,
        OffHireRequestDeletePopupComponent,
    ],
    entryComponents: [
        OffHireRequestComponent,
        OffHireRequestDialogComponent,
        OffHireRequestPopupComponent,
        OffHireRequestDeleteDialogComponent,
        OffHireRequestDeletePopupComponent,
    ],
    providers: [
        OffHireRequestService,
        OffHireRequestPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FleetManagementOffHireRequestModule {}
