import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPatientLifeConstant } from 'app/shared/model/patient-life-constant.model';
import { PatientLifeConstantService } from './patient-life-constant.service';

@Component({
  templateUrl: './patient-life-constant-delete-dialog.component.html'
})
export class PatientLifeConstantDeleteDialogComponent {
  patientLifeConstant: IPatientLifeConstant;

  constructor(
    protected patientLifeConstantService: PatientLifeConstantService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.patientLifeConstantService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'patientLifeConstantListModification',
        content: 'Deleted an patientLifeConstant'
      });
      this.activeModal.dismiss(true);
    });
  }
}
