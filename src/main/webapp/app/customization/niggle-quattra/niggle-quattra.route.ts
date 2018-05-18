import { Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { NiggleListQuattraComponent } from './niggle-list-quattra/niggle-list-quattra.component';
import { CreateDialogQuattraComponent } from './niggle-list-quattra/create-dialog-quattra/create-dialog-quattra.component';
import { EditDialogQuattraComponent } from './niggle-list-quattra/edit-dialog-quattra/edit-dialog-quattra.component';
import { DeleteDialogQuattraComponent } from './niggle-list-quattra/delete-dialog-quattra/delete-dialog-quattra.component';

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
