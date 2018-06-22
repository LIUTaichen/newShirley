import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FleetManagementSharedModule } from '../../shared';
import {
    NiggleSnapshotService,
    NiggleSnapshotPopupService,
    NiggleSnapshotComponent,
    NiggleSnapshotDetailComponent,
    NiggleSnapshotDialogComponent,
    NiggleSnapshotPopupComponent,
    NiggleSnapshotDeletePopupComponent,
    NiggleSnapshotDeleteDialogComponent,
    niggleSnapshotRoute,
    niggleSnapshotPopupRoute,
} from './';

const ENTITY_STATES = [
    ...niggleSnapshotRoute,
    ...niggleSnapshotPopupRoute,
];

@NgModule({
    imports: [
        FleetManagementSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        NiggleSnapshotComponent,
        NiggleSnapshotDetailComponent,
        NiggleSnapshotDialogComponent,
        NiggleSnapshotDeleteDialogComponent,
        NiggleSnapshotPopupComponent,
        NiggleSnapshotDeletePopupComponent,
    ],
    entryComponents: [
        NiggleSnapshotComponent,
        NiggleSnapshotDialogComponent,
        NiggleSnapshotPopupComponent,
        NiggleSnapshotDeleteDialogComponent,
        NiggleSnapshotDeletePopupComponent,
    ],
    providers: [
        NiggleSnapshotService,
        NiggleSnapshotPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FleetManagementNiggleSnapshotModule {}
