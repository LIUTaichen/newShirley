import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomizationRoutingModule } from './customization-routing.module';
import { NiggleDwModule } from './niggle-dw/niggle-dw.module';
import { NiggleQuattraModule } from './niggle-quattra/niggle-quattra.module';

@NgModule({
  imports: [
    CommonModule,
    CustomizationRoutingModule,
    NiggleDwModule,
    NiggleQuattraModule
  ],
  declarations: []
})
export class CustomizationModule { }
