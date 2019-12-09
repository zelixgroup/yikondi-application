import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmergencyAmbulance } from 'app/shared/model/emergency-ambulance.model';
import { EmergencyAmbulanceService } from './emergency-ambulance.service';

@Component({
  templateUrl: './emergency-ambulance-delete-dialog.component.html'
})
export class EmergencyAmbulanceDeleteDialogComponent {
  emergencyAmbulance: IEmergencyAmbulance;

  constructor(
    protected emergencyAmbulanceService: EmergencyAmbulanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.emergencyAmbulanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'emergencyAmbulanceListModification',
        content: 'Deleted an emergencyAmbulance'
      });
      this.activeModal.dismiss(true);
    });
  }
}
