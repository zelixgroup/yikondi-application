import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMedicalPrescriptionAnalysis } from 'app/shared/model/medical-prescription-analysis.model';
import { MedicalPrescriptionAnalysisService } from './medical-prescription-analysis.service';

@Component({
  templateUrl: './medical-prescription-analysis-delete-dialog.component.html'
})
export class MedicalPrescriptionAnalysisDeleteDialogComponent {
  medicalPrescriptionAnalysis: IMedicalPrescriptionAnalysis;

  constructor(
    protected medicalPrescriptionAnalysisService: MedicalPrescriptionAnalysisService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.medicalPrescriptionAnalysisService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'medicalPrescriptionAnalysisListModification',
        content: 'Deleted an medicalPrescriptionAnalysis'
      });
      this.activeModal.dismiss(true);
    });
  }
}
