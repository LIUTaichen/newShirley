import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FleetManagementSharedModule } from '../../shared';
import {
    PrestartQuestionOptionService,
    PrestartQuestionOptionPopupService,
    PrestartQuestionOptionComponent,
    PrestartQuestionOptionDetailComponent,
    PrestartQuestionOptionDialogComponent,
    PrestartQuestionOptionPopupComponent,
    PrestartQuestionOptionDeletePopupComponent,
    PrestartQuestionOptionDeleteDialogComponent,
    prestartQuestionOptionRoute,
    prestartQuestionOptionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...prestartQuestionOptionRoute,
    ...prestartQuestionOptionPopupRoute,
];

@NgModule({
    imports: [
        FleetManagementSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PrestartQuestionOptionComponent,
        PrestartQuestionOptionDetailComponent,
        PrestartQuestionOptionDialogComponent,
        PrestartQuestionOptionDeleteDialogComponent,
        PrestartQuestionOptionPopupComponent,
        PrestartQuestionOptionDeletePopupComponent,
    ],
    entryComponents: [
        PrestartQuestionOptionComponent,
        PrestartQuestionOptionDialogComponent,
        PrestartQuestionOptionPopupComponent,
        PrestartQuestionOptionDeleteDialogComponent,
        PrestartQuestionOptionDeletePopupComponent,
    ],
    providers: [
        PrestartQuestionOptionService,
        PrestartQuestionOptionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FleetManagementPrestartQuestionOptionModule {}
