import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PrestartQuestionComponent } from './prestart-question.component';
import { PrestartQuestionDetailComponent } from './prestart-question-detail.component';
import { PrestartQuestionPopupComponent } from './prestart-question-dialog.component';
import { PrestartQuestionDeletePopupComponent } from './prestart-question-delete-dialog.component';

export const prestartQuestionRoute: Routes = [
    {
        path: 'prestart-question',
        component: PrestartQuestionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartQuestion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prestart-question/:id',
        component: PrestartQuestionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartQuestion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const prestartQuestionPopupRoute: Routes = [
    {
        path: 'prestart-question-new',
        component: PrestartQuestionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartQuestion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prestart-question/:id/edit',
        component: PrestartQuestionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartQuestion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prestart-question/:id/delete',
        component: PrestartQuestionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartQuestion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
