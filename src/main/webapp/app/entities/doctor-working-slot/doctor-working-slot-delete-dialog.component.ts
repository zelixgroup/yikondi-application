import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDoctorWorkingSlot } from 'app/shared/model/doctor-working-slot.model';
import { DoctorWorkingSlotService } from './doctor-working-slot.service';

@Component({
  templateUrl: './doctor-working-slot-delete-dialog.component.html'
})
export class DoctorWorkingSlotDeleteDialogComponent {
  doctorWorkingSlot: IDoctorWorkingSlot;

  constructor(
    protected doctorWorkingSlotService: DoctorWorkingSlotService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.doctorWorkingSlotService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'doctorWorkingSlotListModification',
        content: 'Deleted an doctorWorkingSlot'
      });
      this.activeModal.dismiss(true);
    });
  }
}
