import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NiggleService } from '../entities/niggle/niggle.service';
import { PlantService } from '../entities/plant/plant.service';

import { CustomizationRoutingModule } from './customization-routing.module';
import { FleetManagementSharedModule } from '../shared';
import { NiggleDwModule } from './niggle-dw/niggle-dw.module';
import { NiggleQuattraModule } from './niggle-quattra/niggle-quattra.module';

@NgModule({
  imports: [
    CommonModule,
    CustomizationRoutingModule,
    NiggleDwModule,
    NiggleQuattraModule,
    FleetManagementSharedModule
  ],
  declarations: [],
  providers: [
    NiggleService,
    PlantService
],
})
export class CustomizationModule { }
