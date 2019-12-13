import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YikondiSharedModule } from 'app/shared/shared.module';
import { DoctorAssistantComponent } from './doctor-assistant.component';
import { DoctorAssistantDetailComponent } from './doctor-assistant-detail.component';
import { DoctorAssistantUpdateComponent } from './doctor-assistant-update.component';
import { DoctorAssistantDeleteDialogComponent } from './doctor-assistant-delete-dialog.component';
import { doctorAssistantRoute } from './doctor-assistant.route';

@NgModule({
  imports: [YikondiSharedModule, RouterModule.forChild(doctorAssistantRoute)],
  declarations: [
    DoctorAssistantComponent,
    DoctorAssistantDetailComponent,
    DoctorAssistantUpdateComponent,
    DoctorAssistantDeleteDialogComponent
  ],
  entryComponents: [DoctorAssistantDeleteDialogComponent]
})
export class YikondiDoctorAssistantModule {}
