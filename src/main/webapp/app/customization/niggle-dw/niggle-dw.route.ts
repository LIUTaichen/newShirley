import { Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { NiggleListDwComponent } from './niggle-list-dw/niggle-list-dw.component';
import { NiggleCreateDialogComponent } from './niggle-list-dw/niggle-create-dialog/niggle-create-dialog.component';

export const niggleDwRoute: Routes = [
  {
    path: 'niggle-dw',
    component: NiggleListDwComponent,
    data: {
      authorities: ['ROLE_DW'],
      pageTitle: 'Dempsey Wood Niggles List'
    },
    canActivate: [UserRouteAccessService]
  }];