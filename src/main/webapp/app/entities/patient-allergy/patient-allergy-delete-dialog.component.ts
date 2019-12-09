import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPatientAllergy } from 'app/shared/model/patient-allergy.model';
import { PatientAllergyService } from './patient-allergy.service';

@Component({
  templateUrl: './patient-allergy-delete-dialog.component.html'
})
export class PatientAllergyDeleteDialogComponent {
  patientAllergy: IPatientAllergy;

  constructor(
    protected patientAllergyService: PatientAllergyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.patientAllergyService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'patientAllergyListModification',
        content: 'Deleted an patientAllergy'
      });
      this.activeModal.dismiss(true);
    });
  }
}
