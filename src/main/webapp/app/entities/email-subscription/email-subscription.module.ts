import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FleetManagementSharedModule } from '../../shared';
import {
    EmailSubscriptionService,
    EmailSubscriptionPopupService,
    EmailSubscriptionComponent,
    EmailSubscriptionDetailComponent,
    EmailSubscriptionDialogComponent,
    EmailSubscriptionPopupComponent,
    EmailSubscriptionDeletePopupComponent,
    EmailSubscriptionDeleteDialogComponent,
    emailSubscriptionRoute,
    emailSubscriptionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...emailSubscriptionRoute,
    ...emailSubscriptionPopupRoute,
];

@NgModule({
    imports: [
        FleetManagementSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EmailSubscriptionComponent,
        EmailSubscriptionDetailComponent,
        EmailSubscriptionDialogComponent,
        EmailSubscriptionDeleteDialogComponent,
        EmailSubscriptionPopupComponent,
        EmailSubscriptionDeletePopupComponent,
    ],
    entryComponents: [
        EmailSubscriptionComponent,
        EmailSubscriptionDialogComponent,
        EmailSubscriptionPopupComponent,
        EmailSubscriptionDeleteDialogComponent,
        EmailSubscriptionDeletePopupComponent,
    ],
    providers: [
        EmailSubscriptionService,
        EmailSubscriptionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FleetManagementEmailSubscriptionModule {}
