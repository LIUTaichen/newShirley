import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PrestartCheckConfigComponent } from './prestart-check-config.component';
import { PrestartCheckConfigDetailComponent } from './prestart-check-config-detail.component';
import { PrestartCheckConfigPopupComponent } from './prestart-check-config-dialog.component';
import { PrestartCheckConfigDeletePopupComponent } from './prestart-check-config-delete-dialog.component';

export const prestartCheckConfigRoute: Routes = [
    {
        path: 'prestart-check-config',
        component: PrestartCheckConfigComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheckConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prestart-check-config/:id',
        component: PrestartCheckConfigDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheckConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const prestartCheckConfigPopupRoute: Routes = [
    {
        path: 'prestart-check-config-new',
        component: PrestartCheckConfigPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheckConfig.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prestart-check-config/:id/edit',
        component: PrestartCheckConfigPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheckConfig.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prestart-check-config/:id/delete',
        component: PrestartCheckConfigDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheckConfig.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
