import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPatientPathology } from 'app/shared/model/patient-pathology.model';
import { PatientPathologyService } from './patient-pathology.service';

@Component({
  templateUrl: './patient-pathology-delete-dialog.component.html'
})
export class PatientPathologyDeleteDialogComponent {
  patientPathology: IPatientPathology;

  constructor(
    protected patientPathologyService: PatientPathologyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.patientPathologyService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'patientPathologyListModification',
        content: 'Deleted an patientPathology'
      });
      this.activeModal.dismiss(true);
    });
  }
}
