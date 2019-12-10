import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YikondiSharedModule } from 'app/shared/shared.module';
import { PatientLifeConstantComponent } from './patient-life-constant.component';
import { PatientLifeConstantDetailComponent } from './patient-life-constant-detail.component';
import { PatientLifeConstantUpdateComponent } from './patient-life-constant-update.component';
import { PatientLifeConstantDeleteDialogComponent } from './patient-life-constant-delete-dialog.component';
import { patientLifeConstantRoute } from './patient-life-constant.route';

@NgModule({
  imports: [YikondiSharedModule, RouterModule.forChild(patientLifeConstantRoute)],
  declarations: [
    PatientLifeConstantComponent,
    PatientLifeConstantDetailComponent,
    PatientLifeConstantUpdateComponent,
    PatientLifeConstantDeleteDialogComponent
  ],
  entryComponents: [PatientLifeConstantDeleteDialogComponent]
})
export class YikondiPatientLifeConstantModule {}
