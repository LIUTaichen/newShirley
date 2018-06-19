import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CompetencyComponent } from './competency.component';
import { CompetencyDetailComponent } from './competency-detail.component';
import { CompetencyPopupComponent } from './competency-dialog.component';
import { CompetencyDeletePopupComponent } from './competency-delete-dialog.component';

export const competencyRoute: Routes = [
    {
        path: 'competency',
        component: CompetencyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.competency.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'competency/:id',
        component: CompetencyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.competency.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const competencyPopupRoute: Routes = [
    {
        path: 'competency-new',
        component: CompetencyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.competency.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'competency/:id/edit',
        component: CompetencyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.competency.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'competency/:id/delete',
        component: CompetencyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.competency.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
