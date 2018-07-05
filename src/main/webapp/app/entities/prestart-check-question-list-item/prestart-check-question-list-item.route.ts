import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PrestartCheckQuestionListItemComponent } from './prestart-check-question-list-item.component';
import { PrestartCheckQuestionListItemDetailComponent } from './prestart-check-question-list-item-detail.component';
import { PrestartCheckQuestionListItemPopupComponent } from './prestart-check-question-list-item-dialog.component';
import {
    PrestartCheckQuestionListItemDeletePopupComponent
} from './prestart-check-question-list-item-delete-dialog.component';

export const prestartCheckQuestionListItemRoute: Routes = [
    {
        path: 'prestart-check-question-list-item',
        component: PrestartCheckQuestionListItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheckQuestionListItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prestart-check-question-list-item/:id',
        component: PrestartCheckQuestionListItemDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheckQuestionListItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const prestartCheckQuestionListItemPopupRoute: Routes = [
    {
        path: 'prestart-check-question-list-item-new',
        component: PrestartCheckQuestionListItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheckQuestionListItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prestart-check-question-list-item/:id/edit',
        component: PrestartCheckQuestionListItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheckQuestionListItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prestart-check-question-list-item/:id/delete',
        component: PrestartCheckQuestionListItemDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartCheckQuestionListItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
