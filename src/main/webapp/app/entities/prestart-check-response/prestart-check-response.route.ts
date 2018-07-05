import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PrestartCheckResponseComponent } from './prestart-check-response.component';
import { PrestartCheckResponseDetailComponent } from './prestart-check-response-detail.component';
import { PrestartCheckResponsePopupComponent } from './prestart-check-response-dialog.component';
import { PrestartCheckResponseDeletePopupComponent } from './prestart-check-response-delete-dialog.component';

export const prestartCheckResponseRoute: Routes = [
    {
        path: 'prestart-check-response',
        component: PrestartCheckResponseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheckResponse.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prestart-check-response/:id',
        component: PrestartCheckResponseDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheckResponse.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const prestartCheckResponsePopupRoute: Routes = [
    {
        path: 'prestart-check-response-new',
        component: PrestartCheckResponsePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheckResponse.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prestart-check-response/:id/edit',
        component: PrestartCheckResponsePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheckResponse.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prestart-check-response/:id/delete',
        component: PrestartCheckResponseDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheckResponse.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
