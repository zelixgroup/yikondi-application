import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAllergy } from 'app/shared/model/allergy.model';
import { AllergyService } from './allergy.service';

@Component({
  templateUrl: './allergy-delete-dialog.component.html'
})
export class AllergyDeleteDialogComponent {
  allergy: IAllergy;

  constructor(protected allergyService: AllergyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.allergyService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'allergyListModification',
        content: 'Deleted an allergy'
      });
      this.activeModal.dismiss(true);
    });
  }
}
