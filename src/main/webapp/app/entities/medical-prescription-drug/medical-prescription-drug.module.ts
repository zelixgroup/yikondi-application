import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YikondiSharedModule } from 'app/shared/shared.module';
import { MedicalPrescriptionDrugComponent } from './medical-prescription-drug.component';
import { MedicalPrescriptionDrugDetailComponent } from './medical-prescription-drug-detail.component';
import { MedicalPrescriptionDrugUpdateComponent } from './medical-prescription-drug-update.component';
import { MedicalPrescriptionDrugDeleteDialogComponent } from './medical-prescription-drug-delete-dialog.component';
import { medicalPrescriptionDrugRoute } from './medical-prescription-drug.route';

@NgModule({
  imports: [YikondiSharedModule, RouterModule.forChild(medicalPrescriptionDrugRoute)],
  declarations: [
    MedicalPrescriptionDrugComponent,
    MedicalPrescriptionDrugDetailComponent,
    MedicalPrescriptionDrugUpdateComponent,
    MedicalPrescriptionDrugDeleteDialogComponent
  ],
  entryComponents: [MedicalPrescriptionDrugDeleteDialogComponent]
})
export class YikondiMedicalPrescriptionDrugModule {}
