import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { NiggleComponent } from './niggle.component';
import { NiggleDetailComponent } from './niggle-detail.component';
import { NigglePopupComponent } from './niggle-dialog.component';
import { NiggleDeletePopupComponent } from './niggle-delete-dialog.component';
import { NiggleListDwComponent } from './niggle-list-dw/niggle-list-dw.component';

export const niggleRoute: Routes = [
    {
        path: 'niggle',
        component: NiggleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Niggles'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'niggle/:id',
        component: NiggleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Niggles'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'niggle-dw',
        component: NiggleListDwComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dempsey Wood Niggles List'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'niggle-quattra',
        component: NiggleListDwComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Quattra Niggles List'
        },
        canActivate: [UserRouteAccessService]
    }

];

export const nigglePopupRoute: Routes = [
    {
        path: 'niggle-new',
        component: NigglePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Niggles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'niggle/:id/edit',
        component: NigglePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Niggles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'niggle/:id/delete',
        component: NiggleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Niggles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
