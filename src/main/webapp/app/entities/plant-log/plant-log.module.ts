import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FleetManagementSharedModule } from '../../shared';
import {
    PlantLogService,
    PlantLogPopupService,
    PlantLogComponent,
    PlantLogDetailComponent,
    PlantLogDialogComponent,
    PlantLogPopupComponent,
    PlantLogDeletePopupComponent,
    PlantLogDeleteDialogComponent,
    plantLogRoute,
    plantLogPopupRoute,
} from './';

const ENTITY_STATES = [
    ...plantLogRoute,
    ...plantLogPopupRoute,
];

@NgModule({
    imports: [
        FleetManagementSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PlantLogComponent,
        PlantLogDetailComponent,
        PlantLogDialogComponent,
        PlantLogDeleteDialogComponent,
        PlantLogPopupComponent,
        PlantLogDeletePopupComponent,
    ],
    entryComponents: [
        PlantLogComponent,
        PlantLogDialogComponent,
        PlantLogPopupComponent,
        PlantLogDeleteDialogComponent,
        PlantLogDeletePopupComponent,
    ],
    providers: [
        PlantLogService,
        PlantLogPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FleetManagementPlantLogModule {}
