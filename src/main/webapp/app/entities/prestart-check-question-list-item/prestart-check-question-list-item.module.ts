import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FleetManagementSharedModule } from '../../shared';
import {
    PrestartCheckQuestionListItemService,
    PrestartCheckQuestionListItemPopupService,
    PrestartCheckQuestionListItemComponent,
    PrestartCheckQuestionListItemDetailComponent,
    PrestartCheckQuestionListItemDialogComponent,
    PrestartCheckQuestionListItemPopupComponent,
    PrestartCheckQuestionListItemDeletePopupComponent,
    PrestartCheckQuestionListItemDeleteDialogComponent,
    prestartCheckQuestionListItemRoute,
    prestartCheckQuestionListItemPopupRoute,
} from './';

const ENTITY_STATES = [
    ...prestartCheckQuestionListItemRoute,
    ...prestartCheckQuestionListItemPopupRoute,
];

@NgModule({
    imports: [
        FleetManagementSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PrestartCheckQuestionListItemComponent,
        PrestartCheckQuestionListItemDetailComponent,
        PrestartCheckQuestionListItemDialogComponent,
        PrestartCheckQuestionListItemDeleteDialogComponent,
        PrestartCheckQuestionListItemPopupComponent,
        PrestartCheckQuestionListItemDeletePopupComponent,
    ],
    entryComponents: [
        PrestartCheckQuestionListItemComponent,
        PrestartCheckQuestionListItemDialogComponent,
        PrestartCheckQuestionListItemPopupComponent,
        PrestartCheckQuestionListItemDeleteDialogComponent,
        PrestartCheckQuestionListItemDeletePopupComponent,
    ],
    providers: [
        PrestartCheckQuestionListItemService,
        PrestartCheckQuestionListItemPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FleetManagementPrestartCheckQuestionListItemModule {}
