import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDoctorSchedule } from 'app/shared/model/doctor-schedule.model';
import { DoctorScheduleService } from './doctor-schedule.service';

@Component({
  templateUrl: './doctor-schedule-delete-dialog.component.html'
})
export class DoctorScheduleDeleteDialogComponent {
  doctorSchedule: IDoctorSchedule;

  constructor(
    protected doctorScheduleService: DoctorScheduleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.doctorScheduleService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'doctorScheduleListModification',
        content: 'Deleted an doctorSchedule'
      });
      this.activeModal.dismiss(true);
    });
  }
}
