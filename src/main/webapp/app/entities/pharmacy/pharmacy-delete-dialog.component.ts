import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPharmacy } from 'app/shared/model/pharmacy.model';
import { PharmacyService } from './pharmacy.service';

@Component({
  templateUrl: './pharmacy-delete-dialog.component.html'
})
export class PharmacyDeleteDialogComponent {
  pharmacy: IPharmacy;

  constructor(protected pharmacyService: PharmacyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pharmacyService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'pharmacyListModification',
        content: 'Deleted an pharmacy'
      });
      this.activeModal.dismiss(true);
    });
  }
}
