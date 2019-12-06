import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPatientFavoritePharmacy } from 'app/shared/model/patient-favorite-pharmacy.model';
import { PatientFavoritePharmacyService } from './patient-favorite-pharmacy.service';

@Component({
  templateUrl: './patient-favorite-pharmacy-delete-dialog.component.html'
})
export class PatientFavoritePharmacyDeleteDialogComponent {
  patientFavoritePharmacy: IPatientFavoritePharmacy;

  constructor(
    protected patientFavoritePharmacyService: PatientFavoritePharmacyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.patientFavoritePharmacyService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'patientFavoritePharmacyListModification',
        content: 'Deleted an patientFavoritePharmacy'
      });
      this.activeModal.dismiss(true);
    });
  }
}
