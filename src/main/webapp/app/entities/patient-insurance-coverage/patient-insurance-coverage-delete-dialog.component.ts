import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPatientInsuranceCoverage } from 'app/shared/model/patient-insurance-coverage.model';
import { PatientInsuranceCoverageService } from './patient-insurance-coverage.service';

@Component({
  templateUrl: './patient-insurance-coverage-delete-dialog.component.html'
})
export class PatientInsuranceCoverageDeleteDialogComponent {
  patientInsuranceCoverage: IPatientInsuranceCoverage;

  constructor(
    protected patientInsuranceCoverageService: PatientInsuranceCoverageService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.patientInsuranceCoverageService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'patientInsuranceCoverageListModification',
        content: 'Deleted an patientInsuranceCoverage'
      });
      this.activeModal.dismiss(true);
    });
  }
}
