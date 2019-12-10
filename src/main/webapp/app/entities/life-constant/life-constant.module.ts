import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YikondiSharedModule } from 'app/shared/shared.module';
import { LifeConstantComponent } from './life-constant.component';
import { LifeConstantDetailComponent } from './life-constant-detail.component';
import { LifeConstantUpdateComponent } from './life-constant-update.component';
import { LifeConstantDeleteDialogComponent } from './life-constant-delete-dialog.component';
import { lifeConstantRoute } from './life-constant.route';

@NgModule({
  imports: [YikondiSharedModule, RouterModule.forChild(lifeConstantRoute)],
  declarations: [LifeConstantComponent, LifeConstantDetailComponent, LifeConstantUpdateComponent, LifeConstantDeleteDialogComponent],
  entryComponents: [LifeConstantDeleteDialogComponent]
})
export class YikondiLifeConstantModule {}
