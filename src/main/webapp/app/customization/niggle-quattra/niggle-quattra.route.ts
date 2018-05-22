import { Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { NiggleListQuattraComponent } from './niggle-list-quattra/niggle-list-quattra.component';

export const niggleQuattraRoute: Routes = [
  {
      path: 'niggle-quattra',
      component: NiggleListQuattraComponent,
      data: {
          authorities: ['ROLE_QUATTRA'],
          pageTitle: 'Quattra Niggles List'
      },
      canActivate: [UserRouteAccessService]
  }

];
