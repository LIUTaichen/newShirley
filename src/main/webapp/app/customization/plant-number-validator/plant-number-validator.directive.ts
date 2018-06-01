import { Directive, forwardRef } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, Validator } from '@angular/forms';

export function plantValidator(control: AbstractControl): { [key: string]: any } {
  if (control.value && control.value.id) {
  } else {
    return {
      error: 'empty!!'
    };
  }
  return null;
}

@Directive({
  selector: '[jhiPlantNumberValidator][ngModel], [jhiPlantNumberValidator][formControl] ',
  providers: [
    { provide: NG_VALIDATORS, useExisting: forwardRef(() => PlantNumberValidatorDirective), multi: true }
  ]
})

export class PlantNumberValidatorDirective implements Validator {

  public validator: Function;

  constructor() {

  }
  validate(control: AbstractControl): { [key: string]: any } {
    return plantValidator(control);
  }
}
