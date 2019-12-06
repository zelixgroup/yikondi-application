import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHealthCentreDoctor } from 'app/shared/model/health-centre-doctor.model';
import { HealthCentreDoctorService } from './health-centre-doctor.service';

@Component({
  templateUrl: './health-centre-doctor-delete-dialog.component.html'
})
export class HealthCentreDoctorDeleteDialogComponent {
  healthCentreDoctor: IHealthCentreDoctor;

  constructor(
    protected healthCentreDoctorService: HealthCentreDoctorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.healthCentreDoctorService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'healthCentreDoctorListModification',
        content: 'Deleted an healthCentreDoctor'
      });
      this.activeModal.dismiss(true);
    });
  }
}
