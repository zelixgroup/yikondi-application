import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPatientEmergencyNumber } from 'app/shared/model/patient-emergency-number.model';
import { PatientEmergencyNumberService } from './patient-emergency-number.service';

@Component({
  templateUrl: './patient-emergency-number-delete-dialog.component.html'
})
export class PatientEmergencyNumberDeleteDialogComponent {
  patientEmergencyNumber: IPatientEmergencyNumber;

  constructor(
    protected patientEmergencyNumberService: PatientEmergencyNumberService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.patientEmergencyNumberService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'patientEmergencyNumberListModification',
        content: 'Deleted an patientEmergencyNumber'
      });
      this.activeModal.dismiss(true);
    });
  }
}
