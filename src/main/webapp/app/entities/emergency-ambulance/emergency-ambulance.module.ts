import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YikondiSharedModule } from 'app/shared/shared.module';
import { EmergencyAmbulanceComponent } from './emergency-ambulance.component';
import { EmergencyAmbulanceDetailComponent } from './emergency-ambulance-detail.component';
import { EmergencyAmbulanceUpdateComponent } from './emergency-ambulance-update.component';
import { EmergencyAmbulanceDeleteDialogComponent } from './emergency-ambulance-delete-dialog.component';
import { emergencyAmbulanceRoute } from './emergency-ambulance.route';

@NgModule({
  imports: [YikondiSharedModule, RouterModule.forChild(emergencyAmbulanceRoute)],
  declarations: [
    EmergencyAmbulanceComponent,
    EmergencyAmbulanceDetailComponent,
    EmergencyAmbulanceUpdateComponent,
    EmergencyAmbulanceDeleteDialogComponent
  ],
  entryComponents: [EmergencyAmbulanceDeleteDialogComponent]
})
export class YikondiEmergencyAmbulanceModule {}
