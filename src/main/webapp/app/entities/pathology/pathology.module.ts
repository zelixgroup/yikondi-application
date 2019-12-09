import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YikondiSharedModule } from 'app/shared/shared.module';
import { PathologyComponent } from './pathology.component';
import { PathologyDetailComponent } from './pathology-detail.component';
import { PathologyUpdateComponent } from './pathology-update.component';
import { PathologyDeleteDialogComponent } from './pathology-delete-dialog.component';
import { pathologyRoute } from './pathology.route';

@NgModule({
  imports: [YikondiSharedModule, RouterModule.forChild(pathologyRoute)],
  declarations: [PathologyComponent, PathologyDetailComponent, PathologyUpdateComponent, PathologyDeleteDialogComponent],
  entryComponents: [PathologyDeleteDialogComponent]
})
export class YikondiPathologyModule {}
