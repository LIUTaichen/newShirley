import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FleetManagementSharedModule } from '../../shared';
import {
    PlantService,
    PlantPopupService,
    PlantComponent,
    PlantDetailComponent,
    PlantDialogComponent,
    PlantPopupComponent,
    PlantDeletePopupComponent,
    PlantDeleteDialogComponent,
    plantRoute,
    plantPopupRoute,
} from './';

const ENTITY_STATES = [
    ...plantRoute,
    ...plantPopupRoute,
];

@NgModule({
    imports: [
        FleetManagementSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PlantComponent,
        PlantDetailComponent,
        PlantDialogComponent,
        PlantDeleteDialogComponent,
        PlantPopupComponent,
        PlantDeletePopupComponent,
    ],
    entryComponents: [
        PlantComponent,
        PlantDialogComponent,
        PlantPopupComponent,
        PlantDeleteDialogComponent,
        PlantDeletePopupComponent,
    ],
    providers: [
        PlantService,
        PlantPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FleetManagementPlantModule {}
