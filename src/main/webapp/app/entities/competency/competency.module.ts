import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FleetManagementSharedModule } from '../../shared';
import {
    CompetencyService,
    CompetencyPopupService,
    CompetencyComponent,
    CompetencyDetailComponent,
    CompetencyDialogComponent,
    CompetencyPopupComponent,
    CompetencyDeletePopupComponent,
    CompetencyDeleteDialogComponent,
    competencyRoute,
    competencyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...competencyRoute,
    ...competencyPopupRoute,
];

@NgModule({
    imports: [
        FleetManagementSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompetencyComponent,
        CompetencyDetailComponent,
        CompetencyDialogComponent,
        CompetencyDeleteDialogComponent,
        CompetencyPopupComponent,
        CompetencyDeletePopupComponent,
    ],
    entryComponents: [
        CompetencyComponent,
        CompetencyDialogComponent,
        CompetencyPopupComponent,
        CompetencyDeleteDialogComponent,
        CompetencyDeletePopupComponent,
    ],
    providers: [
        CompetencyService,
        CompetencyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FleetManagementCompetencyModule {}
