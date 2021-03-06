import { Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { NiggleListDwComponent } from './niggle-list-dw/niggle-list-dw.component';

export const niggleDwRoute: Routes = [
  {
    path: 'pf-dw',
    component: NiggleListDwComponent,
    data: {
      authorities: ['ROLE_DW', 'ROLE_DW_READ_ONLY'],
      pageTitle: 'fleetManagementApp.niggle.dw.home.title'
    },
    canActivate: [UserRouteAccessService]
  }];
