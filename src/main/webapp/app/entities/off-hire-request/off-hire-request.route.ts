import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { OffHireRequestComponent } from './off-hire-request.component';
import { OffHireRequestDetailComponent } from './off-hire-request-detail.component';
import { OffHireRequestPopupComponent } from './off-hire-request-dialog.component';
import { OffHireRequestDeletePopupComponent } from './off-hire-request-delete-dialog.component';

export const offHireRequestRoute: Routes = [
    {
        path: 'off-hire-request',
        component: OffHireRequestComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OffHireRequests'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'off-hire-request/:id',
        component: OffHireRequestDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OffHireRequests'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const offHireRequestPopupRoute: Routes = [
    {
        path: 'off-hire-request-new',
        component: OffHireRequestPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OffHireRequests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'off-hire-request/:id/edit',
        component: OffHireRequestPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OffHireRequests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'off-hire-request/:id/delete',
        component: OffHireRequestDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OffHireRequests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
