import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { NiggleSnapshotComponent } from './niggle-snapshot.component';
import { NiggleSnapshotDetailComponent } from './niggle-snapshot-detail.component';
import { NiggleSnapshotPopupComponent } from './niggle-snapshot-dialog.component';
import { NiggleSnapshotDeletePopupComponent } from './niggle-snapshot-delete-dialog.component';

export const niggleSnapshotRoute: Routes = [
    {
        path: 'niggle-snapshot',
        component: NiggleSnapshotComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.niggleSnapshot.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'niggle-snapshot/:id',
        component: NiggleSnapshotDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.niggleSnapshot.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const niggleSnapshotPopupRoute: Routes = [
    {
        path: 'niggle-snapshot-new',
        component: NiggleSnapshotPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.niggleSnapshot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'niggle-snapshot/:id/edit',
        component: NiggleSnapshotPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.niggleSnapshot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'niggle-snapshot/:id/delete',
        component: NiggleSnapshotDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.niggleSnapshot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
