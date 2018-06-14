import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoute } from './dashboard.route';
import { DashboardComponent } from './dashboard/dashboard.component';
import { OverDueReportComponent } from './over-due-report/over-due-report.component';
import { OpenReportComponent } from './open-report/open-report.component';
import { MaterialModule } from '../../material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { FleetManagementSharedModule } from '../../shared';
import { RouterModule } from '@angular/router';

const ENTITY_STATES = [
  ...DashboardRoute,
];

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FleetManagementSharedModule,
    RouterModule.forChild(ENTITY_STATES)
  ],
  declarations: [DashboardComponent, OverDueReportComponent, OpenReportComponent]
})
export class DashboardModule { }
