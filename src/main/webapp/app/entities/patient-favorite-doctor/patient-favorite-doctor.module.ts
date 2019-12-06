import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YikondiSharedModule } from 'app/shared/shared.module';
import { PatientFavoriteDoctorComponent } from './patient-favorite-doctor.component';
import { PatientFavoriteDoctorDetailComponent } from './patient-favorite-doctor-detail.component';
import { PatientFavoriteDoctorUpdateComponent } from './patient-favorite-doctor-update.component';
import { PatientFavoriteDoctorDeleteDialogComponent } from './patient-favorite-doctor-delete-dialog.component';
import { patientFavoriteDoctorRoute } from './patient-favorite-doctor.route';

@NgModule({
  imports: [YikondiSharedModule, RouterModule.forChild(patientFavoriteDoctorRoute)],
  declarations: [
    PatientFavoriteDoctorComponent,
    PatientFavoriteDoctorDetailComponent,
    PatientFavoriteDoctorUpdateComponent,
    PatientFavoriteDoctorDeleteDialogComponent
  ],
  entryComponents: [PatientFavoriteDoctorDeleteDialogComponent]
})
export class YikondiPatientFavoriteDoctorModule {}
