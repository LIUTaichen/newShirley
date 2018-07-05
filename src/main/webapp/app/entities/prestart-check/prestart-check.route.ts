import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PrestartCheckComponent } from './prestart-check.component';
import { PrestartCheckDetailComponent } from './prestart-check-detail.component';
import { PrestartCheckPopupComponent } from './prestart-check-dialog.component';
import { PrestartCheckDeletePopupComponent } from './prestart-check-delete-dialog.component';

export const prestartCheckRoute: Routes = [
    {
        path: 'prestart-check',
        component: PrestartCheckComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheck.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prestart-check/:id',
        component: PrestartCheckDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheck.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const prestartCheckPopupRoute: Routes = [
    {
        path: 'prestart-check-new',
        component: PrestartCheckPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheck.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prestart-check/:id/edit',
        component: PrestartCheckPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheck.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prestart-check/:id/delete',
        component: PrestartCheckDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheck.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
