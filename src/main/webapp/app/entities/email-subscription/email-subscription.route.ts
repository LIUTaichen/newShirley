import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EmailSubscriptionComponent } from './email-subscription.component';
import { EmailSubscriptionDetailComponent } from './email-subscription-detail.component';
import { EmailSubscriptionPopupComponent } from './email-subscription-dialog.component';
import { EmailSubscriptionDeletePopupComponent } from './email-subscription-delete-dialog.component';

export const emailSubscriptionRoute: Routes = [
    {
        path: 'email-subscription',
        component: EmailSubscriptionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.emailSubscription.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'email-subscription/:id',
        component: EmailSubscriptionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.emailSubscription.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const emailSubscriptionPopupRoute: Routes = [
    {
        path: 'email-subscription-new',
        component: EmailSubscriptionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.emailSubscription.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'email-subscription/:id/edit',
        component: EmailSubscriptionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.emailSubscription.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'email-subscription/:id/delete',
        component: EmailSubscriptionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.emailSubscription.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
