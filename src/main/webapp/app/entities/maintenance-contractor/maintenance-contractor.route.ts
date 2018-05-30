import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MaintenanceContractorComponent } from './maintenance-contractor.component';
import { MaintenanceContractorDetailComponent } from './maintenance-contractor-detail.component';
import { MaintenanceContractorPopupComponent } from './maintenance-contractor-dialog.component';
import { MaintenanceContractorDeletePopupComponent } from './maintenance-contractor-delete-dialog.component';

export const maintenanceContractorRoute: Routes = [
    {
        path: 'maintenance-contractor',
        component: MaintenanceContractorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.maintenanceContractor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'maintenance-contractor/:id',
        component: MaintenanceContractorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.maintenanceContractor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const maintenanceContractorPopupRoute: Routes = [
    {
        path: 'maintenance-contractor-new',
        component: MaintenanceContractorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.maintenanceContractor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'maintenance-contractor/:id/edit',
        component: MaintenanceContractorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.maintenanceContractor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'maintenance-contractor/:id/delete',
        component: MaintenanceContractorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.maintenanceContractor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
