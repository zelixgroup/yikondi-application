import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YikondiSharedModule } from 'app/shared/shared.module';
import { AnalysisComponent } from './analysis.component';
import { AnalysisDetailComponent } from './analysis-detail.component';
import { AnalysisUpdateComponent } from './analysis-update.component';
import { AnalysisDeleteDialogComponent } from './analysis-delete-dialog.component';
import { analysisRoute } from './analysis.route';

@NgModule({
  imports: [YikondiSharedModule, RouterModule.forChild(analysisRoute)],
  declarations: [AnalysisComponent, AnalysisDetailComponent, AnalysisUpdateComponent, AnalysisDeleteDialogComponent],
  entryComponents: [AnalysisDeleteDialogComponent]
})
export class YikondiAnalysisModule {}
