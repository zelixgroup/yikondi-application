import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YikondiSharedModule } from 'app/shared/shared.module';
import { DrugAdministrationRouteComponent } from './drug-administration-route.component';
import { DrugAdministrationRouteDetailComponent } from './drug-administration-route-detail.component';
import { DrugAdministrationRouteUpdateComponent } from './drug-administration-route-update.component';
import { DrugAdministrationRouteDeleteDialogComponent } from './drug-administration-route-delete-dialog.component';
import { drugAdministrationRouteRoute } from './drug-administration-route.route';

@NgModule({
  imports: [YikondiSharedModule, RouterModule.forChild(drugAdministrationRouteRoute)],
  declarations: [
    DrugAdministrationRouteComponent,
    DrugAdministrationRouteDetailComponent,
    DrugAdministrationRouteUpdateComponent,
    DrugAdministrationRouteDeleteDialogComponent
  ],
  entryComponents: [DrugAdministrationRouteDeleteDialogComponent]
})
export class YikondiDrugAdministrationRouteModule {}
