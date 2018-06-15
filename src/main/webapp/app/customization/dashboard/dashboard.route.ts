import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { UserRouteAccessService } from '../../shared';

export const DashboardRoute: Routes = [
  {
      path: 'dashboard',
      component: DashboardComponent,
      data: {
          authorities: ['ROLE_DW', 'ROLE_DW_READ_ONLY'],
          pageTitle: 'fleetManagementApp.dashboard.home.title'
      },
      canActivate: [UserRouteAccessService]
  }

];
