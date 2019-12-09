import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YikondiSharedModule } from 'app/shared/shared.module';
import { PatientEmergencyNumberComponent } from './patient-emergency-number.component';
import { PatientEmergencyNumberDetailComponent } from './patient-emergency-number-detail.component';
import { PatientEmergencyNumberUpdateComponent } from './patient-emergency-number-update.component';
import { PatientEmergencyNumberDeleteDialogComponent } from './patient-emergency-number-delete-dialog.component';
import { patientEmergencyNumberRoute } from './patient-emergency-number.route';

@NgModule({
  imports: [YikondiSharedModule, RouterModule.forChild(patientEmergencyNumberRoute)],
  declarations: [
    PatientEmergencyNumberComponent,
    PatientEmergencyNumberDetailComponent,
    PatientEmergencyNumberUpdateComponent,
    PatientEmergencyNumberDeleteDialogComponent
  ],
  entryComponents: [PatientEmergencyNumberDeleteDialogComponent]
})
export class YikondiPatientEmergencyNumberModule {}
