import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PrestartQuestionOptionComponent } from './prestart-question-option.component';
import { PrestartQuestionOptionDetailComponent } from './prestart-question-option-detail.component';
import { PrestartQuestionOptionPopupComponent } from './prestart-question-option-dialog.component';
import { PrestartQuestionOptionDeletePopupComponent } from './prestart-question-option-delete-dialog.component';

export const prestartQuestionOptionRoute: Routes = [
    {
        path: 'prestart-question-option',
        component: PrestartQuestionOptionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartQuestionOption.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prestart-question-option/:id',
        component: PrestartQuestionOptionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartQuestionOption.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const prestartQuestionOptionPopupRoute: Routes = [
    {
        path: 'prestart-question-option-new',
        component: PrestartQuestionOptionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartQuestionOption.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prestart-question-option/:id/edit',
        component: PrestartQuestionOptionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartQuestionOption.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prestart-question-option/:id/delete',
        component: PrestartQuestionOptionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fleetManagementApp.prestartQuestionOption.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
