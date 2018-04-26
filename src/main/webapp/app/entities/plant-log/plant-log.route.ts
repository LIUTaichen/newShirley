import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PlantLogComponent } from './plant-log.component';
import { PlantLogDetailComponent } from './plant-log-detail.component';
import { PlantLogPopupComponent } from './plant-log-dialog.component';
import { PlantLogDeletePopupComponent } from './plant-log-delete-dialog.component';

export const plantLogRoute: Routes = [
    {
        path: 'plant-log',
        component: PlantLogComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlantLogs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'plant-log/:id',
        component: PlantLogDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlantLogs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const plantLogPopupRoute: Routes = [
    {
        path: 'plant-log-new',
        component: PlantLogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlantLogs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'plant-log/:id/edit',
        component: PlantLogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlantLogs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'plant-log/:id/delete',
        component: PlantLogDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlantLogs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
