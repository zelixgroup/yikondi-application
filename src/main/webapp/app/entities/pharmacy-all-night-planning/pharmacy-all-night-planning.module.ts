import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YikondiSharedModule } from 'app/shared/shared.module';
import { PharmacyAllNightPlanningComponent } from './pharmacy-all-night-planning.component';
import { PharmacyAllNightPlanningDetailComponent } from './pharmacy-all-night-planning-detail.component';
import { PharmacyAllNightPlanningUpdateComponent } from './pharmacy-all-night-planning-update.component';
import { PharmacyAllNightPlanningDeleteDialogComponent } from './pharmacy-all-night-planning-delete-dialog.component';
import { pharmacyAllNightPlanningRoute } from './pharmacy-all-night-planning.route';

@NgModule({
  imports: [YikondiSharedModule, RouterModule.forChild(pharmacyAllNightPlanningRoute)],
  declarations: [
    PharmacyAllNightPlanningComponent,
    PharmacyAllNightPlanningDetailComponent,
    PharmacyAllNightPlanningUpdateComponent,
    PharmacyAllNightPlanningDeleteDialogComponent
  ],
  entryComponents: [PharmacyAllNightPlanningDeleteDialogComponent]
})
export class YikondiPharmacyAllNightPlanningModule {}
