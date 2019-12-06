import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHoliday } from 'app/shared/model/holiday.model';
import { HolidayService } from './holiday.service';

@Component({
  templateUrl: './holiday-delete-dialog.component.html'
})
export class HolidayDeleteDialogComponent {
  holiday: IHoliday;

  constructor(protected holidayService: HolidayService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.holidayService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'holidayListModification',
        content: 'Deleted an holiday'
      });
      this.activeModal.dismiss(true);
    });
  }
}
