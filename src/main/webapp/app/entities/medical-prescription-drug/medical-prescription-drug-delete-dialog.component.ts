import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMedicalPrescriptionDrug } from 'app/shared/model/medical-prescription-drug.model';
import { MedicalPrescriptionDrugService } from './medical-prescription-drug.service';

@Component({
  templateUrl: './medical-prescription-drug-delete-dialog.component.html'
})
export class MedicalPrescriptionDrugDeleteDialogComponent {
  medicalPrescriptionDrug: IMedicalPrescriptionDrug;

  constructor(
    protected medicalPrescriptionDrugService: MedicalPrescriptionDrugService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.medicalPrescriptionDrugService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'medicalPrescriptionDrugListModification',
        content: 'Deleted an medicalPrescriptionDrug'
      });
      this.activeModal.dismiss(true);
    });
  }
}
