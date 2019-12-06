import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPharmacyAllNightPlanning } from 'app/shared/model/pharmacy-all-night-planning.model';
import { PharmacyAllNightPlanningService } from './pharmacy-all-night-planning.service';

@Component({
  templateUrl: './pharmacy-all-night-planning-delete-dialog.component.html'
})
export class PharmacyAllNightPlanningDeleteDialogComponent {
  pharmacyAllNightPlanning: IPharmacyAllNightPlanning;

  constructor(
    protected pharmacyAllNightPlanningService: PharmacyAllNightPlanningService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pharmacyAllNightPlanningService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'pharmacyAllNightPlanningListModification',
        content: 'Deleted an pharmacyAllNightPlanning'
      });
      this.activeModal.dismiss(true);
    });
  }
}
