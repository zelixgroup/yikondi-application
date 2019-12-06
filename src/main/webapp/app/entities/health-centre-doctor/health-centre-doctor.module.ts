import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YikondiSharedModule } from 'app/shared/shared.module';
import { HealthCentreDoctorComponent } from './health-centre-doctor.component';
import { HealthCentreDoctorDetailComponent } from './health-centre-doctor-detail.component';
import { HealthCentreDoctorUpdateComponent } from './health-centre-doctor-update.component';
import { HealthCentreDoctorDeleteDialogComponent } from './health-centre-doctor-delete-dialog.component';
import { healthCentreDoctorRoute } from './health-centre-doctor.route';

@NgModule({
  imports: [YikondiSharedModule, RouterModule.forChild(healthCentreDoctorRoute)],
  declarations: [
    HealthCentreDoctorComponent,
    HealthCentreDoctorDetailComponent,
    HealthCentreDoctorUpdateComponent,
    HealthCentreDoctorDeleteDialogComponent
  ],
  entryComponents: [HealthCentreDoctorDeleteDialogComponent]
})
export class YikondiHealthCentreDoctorModule {}
