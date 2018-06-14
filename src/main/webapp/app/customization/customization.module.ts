import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NiggleService } from '../entities/niggle/niggle.service';
import { PlantService } from '../entities/plant/plant.service';

import { CustomizationRoutingModule } from './customization-routing.module';
import { FleetManagementSharedModule } from '../shared';
import { NiggleDwModule } from './niggle-dw/niggle-dw.module';
import { NiggleQuattraModule } from './niggle-quattra/niggle-quattra.module';
import { PlantNumberValidatorDirective } from './plant-number-validator/plant-number-validator.directive';
import { DashboardModule } from './dashboard/dashboard.module';
@NgModule({
  imports: [
    CommonModule,
    CustomizationRoutingModule,
    NiggleDwModule,
    NiggleQuattraModule,
    FleetManagementSharedModule,
    DashboardModule,
  ],
  declarations: [
    PlantNumberValidatorDirective
  ],
  providers: [
    NiggleService,
    PlantService
],
})
export class CustomizationModule { }
