import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { WeeklyNiggleSnapshotComponent } from './weekly-niggle-snapshot.component';
import { WeeklyNiggleSnapshotDetailComponent } from './weekly-niggle-snapshot-detail.component';
import { WeeklyNiggleSnapshotPopupComponent } from './weekly-niggle-snapshot-dialog.component';
import { WeeklyNiggleSnapshotDeletePopupComponent } from './weekly-niggle-snapshot-delete-dialog.component';

export const weeklyNiggleSnapshotRoute: Routes = [
    {
        path: 'weekly-niggle-snapshot',
        component: WeeklyNiggleSnapshotComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.weeklyNiggleSnapshot.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'weekly-niggle-snapshot/:id',
        component: WeeklyNiggleSnapshotDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.weeklyNiggleSnapshot.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const weeklyNiggleSnapshotPopupRoute: Routes = [
    {
        path: 'weekly-niggle-snapshot-new',
        component: WeeklyNiggleSnapshotPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.weeklyNiggleSnapshot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'weekly-niggle-snapshot/:id/edit',
        component: WeeklyNiggleSnapshotPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.weeklyNiggleSnapshot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'weekly-niggle-snapshot/:id/delete',
        component: WeeklyNiggleSnapshotDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.weeklyNiggleSnapshot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
