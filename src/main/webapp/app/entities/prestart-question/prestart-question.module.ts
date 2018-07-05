import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FleetManagementSharedModule } from '../../shared';
import {
    PrestartQuestionService,
    PrestartQuestionPopupService,
    PrestartQuestionComponent,
    PrestartQuestionDetailComponent,
    PrestartQuestionDialogComponent,
    PrestartQuestionPopupComponent,
    PrestartQuestionDeletePopupComponent,
    PrestartQuestionDeleteDialogComponent,
    prestartQuestionRoute,
    prestartQuestionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...prestartQuestionRoute,
    ...prestartQuestionPopupRoute,
];

@NgModule({
    imports: [
        FleetManagementSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PrestartQuestionComponent,
        PrestartQuestionDetailComponent,
        PrestartQuestionDialogComponent,
        PrestartQuestionDeleteDialogComponent,
        PrestartQuestionPopupComponent,
        PrestartQuestionDeletePopupComponent,
    ],
    entryComponents: [
        PrestartQuestionComponent,
        PrestartQuestionDialogComponent,
        PrestartQuestionPopupComponent,
        PrestartQuestionDeleteDialogComponent,
        PrestartQuestionDeletePopupComponent,
    ],
    providers: [
        PrestartQuestionService,
        PrestartQuestionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FleetManagementPrestartQuestionModule {}
