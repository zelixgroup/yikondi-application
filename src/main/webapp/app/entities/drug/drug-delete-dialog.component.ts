import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDrug } from 'app/shared/model/drug.model';
import { DrugService } from './drug.service';

@Component({
  templateUrl: './drug-delete-dialog.component.html'
})
export class DrugDeleteDialogComponent {
  drug: IDrug;

  constructor(protected drugService: DrugService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.drugService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'drugListModification',
        content: 'Deleted an drug'
      });
      this.activeModal.dismiss(true);
    });
  }
}
