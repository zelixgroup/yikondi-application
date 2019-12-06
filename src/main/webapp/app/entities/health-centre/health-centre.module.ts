import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YikondiSharedModule } from 'app/shared/shared.module';
import { HealthCentreComponent } from './health-centre.component';
import { HealthCentreDetailComponent } from './health-centre-detail.component';
import { HealthCentreUpdateComponent } from './health-centre-update.component';
import { HealthCentreDeleteDialogComponent } from './health-centre-delete-dialog.component';
import { healthCentreRoute } from './health-centre.route';

@NgModule({
  imports: [YikondiSharedModule, RouterModule.forChild(healthCentreRoute)],
  declarations: [HealthCentreComponent, HealthCentreDetailComponent, HealthCentreUpdateComponent, HealthCentreDeleteDialogComponent],
  entryComponents: [HealthCentreDeleteDialogComponent]
})
export class YikondiHealthCentreModule {}
