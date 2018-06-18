import { NgModule } from '@angular/core';

import {
  MatButtonModule,
  MatMenuModule,
  MatToolbarModule,
  MatIconModule,
  MatCardModule,
  MatDatepickerModule,
  MatTableModule,
  MatRadioModule,
  MatFormFieldModule,
  MatInputModule,
  MatSortModule,
  MatDialogModule,
  MatSelectModule,
  MatAutocompleteModule,
  MatRippleModule,
  MatSnackBarModule,
  MatDividerModule,
  MatProgressSpinnerModule,
  MAT_DATE_LOCALE,
} from '@angular/material';
import { MatMomentDateModule } from '@angular/material-moment-adapter';

@NgModule({
  imports: [
    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule,
    MatDatepickerModule,
    MatTableModule,
    MatRadioModule,
    MatFormFieldModule,
    MatInputModule,
    MatSortModule,
    MatDialogModule,
    MatSelectModule,
    MatAutocompleteModule,
    MatRippleModule,
    MatSnackBarModule,
    MatDividerModule,
    MatProgressSpinnerModule,
    MatMomentDateModule
  ],
  exports: [
    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule,
    MatDatepickerModule,
    MatTableModule,
    MatRadioModule,
    MatFormFieldModule,
    MatInputModule,
    MatSortModule,
    MatDialogModule,
    MatSelectModule,
    MatAutocompleteModule,
    MatRippleModule,
    MatSnackBarModule,
    MatDividerModule,
    MatProgressSpinnerModule,
    MatMomentDateModule
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'en-NZ'},
  ]
})
export class MaterialModule {}
