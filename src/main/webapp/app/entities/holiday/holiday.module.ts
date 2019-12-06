import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YikondiSharedModule } from 'app/shared/shared.module';
import { HolidayComponent } from './holiday.component';
import { HolidayDetailComponent } from './holiday-detail.component';
import { HolidayUpdateComponent } from './holiday-update.component';
import { HolidayDeleteDialogComponent } from './holiday-delete-dialog.component';
import { holidayRoute } from './holiday.route';

@NgModule({
  imports: [YikondiSharedModule, RouterModule.forChild(holidayRoute)],
  declarations: [HolidayComponent, HolidayDetailComponent, HolidayUpdateComponent, HolidayDeleteDialogComponent],
  entryComponents: [HolidayDeleteDialogComponent]
})
export class YikondiHolidayModule {}
