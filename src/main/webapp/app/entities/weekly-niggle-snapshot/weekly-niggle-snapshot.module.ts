import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FleetManagementSharedModule } from '../../shared';
import {
    WeeklyNiggleSnapshotService,
    WeeklyNiggleSnapshotPopupService,
    WeeklyNiggleSnapshotComponent,
    WeeklyNiggleSnapshotDetailComponent,
    WeeklyNiggleSnapshotDialogComponent,
    WeeklyNiggleSnapshotPopupComponent,
    WeeklyNiggleSnapshotDeletePopupComponent,
    WeeklyNiggleSnapshotDeleteDialogComponent,
    weeklyNiggleSnapshotRoute,
    weeklyNiggleSnapshotPopupRoute,
} from './';

const ENTITY_STATES = [
    ...weeklyNiggleSnapshotRoute,
    ...weeklyNiggleSnapshotPopupRoute,
];

@NgModule({
    imports: [
        FleetManagementSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        WeeklyNiggleSnapshotComponent,
        WeeklyNiggleSnapshotDetailComponent,
        WeeklyNiggleSnapshotDialogComponent,
        WeeklyNiggleSnapshotDeleteDialogComponent,
        WeeklyNiggleSnapshotPopupComponent,
        WeeklyNiggleSnapshotDeletePopupComponent,
    ],
    entryComponents: [
        WeeklyNiggleSnapshotComponent,
        WeeklyNiggleSnapshotDialogComponent,
        WeeklyNiggleSnapshotPopupComponent,
        WeeklyNiggleSnapshotDeleteDialogComponent,
        WeeklyNiggleSnapshotDeletePopupComponent,
    ],
    providers: [
        WeeklyNiggleSnapshotService,
        WeeklyNiggleSnapshotPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FleetManagementWeeklyNiggleSnapshotModule {}
