import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YikondiSharedModule } from 'app/shared/shared.module';
import { MedicalRecordAuthorizationComponent } from './medical-record-authorization.component';
import { MedicalRecordAuthorizationDetailComponent } from './medical-record-authorization-detail.component';
import { MedicalRecordAuthorizationUpdateComponent } from './medical-record-authorization-update.component';
import { MedicalRecordAuthorizationDeleteDialogComponent } from './medical-record-authorization-delete-dialog.component';
import { medicalRecordAuthorizationRoute } from './medical-record-authorization.route';

@NgModule({
  imports: [YikondiSharedModule, RouterModule.forChild(medicalRecordAuthorizationRoute)],
  declarations: [
    MedicalRecordAuthorizationComponent,
    MedicalRecordAuthorizationDetailComponent,
    MedicalRecordAuthorizationUpdateComponent,
    MedicalRecordAuthorizationDeleteDialogComponent
  ],
  entryComponents: [MedicalRecordAuthorizationDeleteDialogComponent]
})
export class YikondiMedicalRecordAuthorizationModule {}
