import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YikondiSharedModule } from 'app/shared/shared.module';
import { PatientFavoritePharmacyComponent } from './patient-favorite-pharmacy.component';
import { PatientFavoritePharmacyDetailComponent } from './patient-favorite-pharmacy-detail.component';
import { PatientFavoritePharmacyUpdateComponent } from './patient-favorite-pharmacy-update.component';
import { PatientFavoritePharmacyDeleteDialogComponent } from './patient-favorite-pharmacy-delete-dialog.component';
import { patientFavoritePharmacyRoute } from './patient-favorite-pharmacy.route';

@NgModule({
  imports: [YikondiSharedModule, RouterModule.forChild(patientFavoritePharmacyRoute)],
  declarations: [
    PatientFavoritePharmacyComponent,
    PatientFavoritePharmacyDetailComponent,
    PatientFavoritePharmacyUpdateComponent,
    PatientFavoritePharmacyDeleteDialogComponent
  ],
  entryComponents: [PatientFavoritePharmacyDeleteDialogComponent]
})
export class YikondiPatientFavoritePharmacyModule {}
