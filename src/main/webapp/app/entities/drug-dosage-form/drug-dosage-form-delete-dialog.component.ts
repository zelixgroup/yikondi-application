import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDrugDosageForm } from 'app/shared/model/drug-dosage-form.model';
import { DrugDosageFormService } from './drug-dosage-form.service';

@Component({
  templateUrl: './drug-dosage-form-delete-dialog.component.html'
})
export class DrugDosageFormDeleteDialogComponent {
  drugDosageForm: IDrugDosageForm;

  constructor(
    protected drugDosageFormService: DrugDosageFormService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.drugDosageFormService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'drugDosageFormListModification',
        content: 'Deleted an drugDosageForm'
      });
      this.activeModal.dismiss(true);
    });
  }
}
