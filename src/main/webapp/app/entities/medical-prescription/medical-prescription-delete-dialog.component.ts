import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMedicalPrescription } from 'app/shared/model/medical-prescription.model';
import { MedicalPrescriptionService } from './medical-prescription.service';

@Component({
  templateUrl: './medical-prescription-delete-dialog.component.html'
})
export class MedicalPrescriptionDeleteDialogComponent {
  medicalPrescription: IMedicalPrescription;

  constructor(
    protected medicalPrescriptionService: MedicalPrescriptionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.medicalPrescriptionService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'medicalPrescriptionListModification',
        content: 'Deleted an medicalPrescription'
      });
      this.activeModal.dismiss(true);
    });
  }
}
