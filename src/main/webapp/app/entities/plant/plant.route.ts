import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PlantComponent } from './plant.component';
import { PlantDetailComponent } from './plant-detail.component';
import { PlantPopupComponent } from './plant-dialog.component';
import { PlantDeletePopupComponent } from './plant-delete-dialog.component';

export const plantRoute: Routes = [
    {
        path: 'plant',
        component: PlantComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Plants'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'plant/:id',
        component: PlantDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Plants'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const plantPopupRoute: Routes = [
    {
        path: 'plant-new',
        component: PlantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Plants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'plant/:id/edit',
        component: PlantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Plants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'plant/:id/delete',
        component: PlantDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Plants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
