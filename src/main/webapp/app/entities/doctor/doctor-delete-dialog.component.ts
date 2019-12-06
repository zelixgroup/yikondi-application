import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDoctor } from 'app/shared/model/doctor.model';
import { DoctorService } from './doctor.service';

@Component({
  templateUrl: './doctor-delete-dialog.component.html'
})
export class DoctorDeleteDialogComponent {
  doctor: IDoctor;

  constructor(protected doctorService: DoctorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.doctorService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'doctorListModification',
        content: 'Deleted an doctor'
      });
      this.activeModal.dismiss(true);
    });
  }
}
