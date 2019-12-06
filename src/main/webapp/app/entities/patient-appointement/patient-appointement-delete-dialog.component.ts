import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPatientAppointement } from 'app/shared/model/patient-appointement.model';
import { PatientAppointementService } from './patient-appointement.service';

@Component({
  templateUrl: './patient-appointement-delete-dialog.component.html'
})
export class PatientAppointementDeleteDialogComponent {
  patientAppointement: IPatientAppointement;

  constructor(
    protected patientAppointementService: PatientAppointementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.patientAppointementService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'patientAppointementListModification',
        content: 'Deleted an patientAppointement'
      });
      this.activeModal.dismiss(true);
    });
  }
}
