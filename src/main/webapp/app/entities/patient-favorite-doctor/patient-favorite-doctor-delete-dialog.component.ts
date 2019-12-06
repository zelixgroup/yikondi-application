import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPatientFavoriteDoctor } from 'app/shared/model/patient-favorite-doctor.model';
import { PatientFavoriteDoctorService } from './patient-favorite-doctor.service';

@Component({
  templateUrl: './patient-favorite-doctor-delete-dialog.component.html'
})
export class PatientFavoriteDoctorDeleteDialogComponent {
  patientFavoriteDoctor: IPatientFavoriteDoctor;

  constructor(
    protected patientFavoriteDoctorService: PatientFavoriteDoctorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.patientFavoriteDoctorService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'patientFavoriteDoctorListModification',
        content: 'Deleted an patientFavoriteDoctor'
      });
      this.activeModal.dismiss(true);
    });
  }
}
