import { Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { NiggleListQuattraComponent } from './niggle-list-quattra/niggle-list-quattra.component';

export const niggleQuattraRoute: Routes = [
  {
      path: 'pf-quattra',
      component: NiggleListQuattraComponent,
      data: {
          authorities: ['ROLE_QUATTRA'],
          pageTitle: 'fleetManagementApp.niggle.quattra.home.title'
      },
      canActivate: [UserRouteAccessService]
  }

];
