import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FleetManagementSharedModule } from '../../shared';
import {
    MaintenanceContractorService,
    MaintenanceContractorPopupService,
    MaintenanceContractorComponent,
    MaintenanceContractorDetailComponent,
    MaintenanceContractorDialogComponent,
    MaintenanceContractorPopupComponent,
    MaintenanceContractorDeletePopupComponent,
    MaintenanceContractorDeleteDialogComponent,
    maintenanceContractorRoute,
    maintenanceContractorPopupRoute,
} from './';

const ENTITY_STATES = [
    ...maintenanceContractorRoute,
    ...maintenanceContractorPopupRoute,
];

@NgModule({
    imports: [
        FleetManagementSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MaintenanceContractorComponent,
        MaintenanceContractorDetailComponent,
        MaintenanceContractorDialogComponent,
        MaintenanceContractorDeleteDialogComponent,
        MaintenanceContractorPopupComponent,
        MaintenanceContractorDeletePopupComponent,
    ],
    entryComponents: [
        MaintenanceContractorComponent,
        MaintenanceContractorDialogComponent,
        MaintenanceContractorPopupComponent,
        MaintenanceContractorDeleteDialogComponent,
        MaintenanceContractorDeletePopupComponent,
    ],
    providers: [
        MaintenanceContractorService,
        MaintenanceContractorPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FleetManagementMaintenanceContractorModule {}
