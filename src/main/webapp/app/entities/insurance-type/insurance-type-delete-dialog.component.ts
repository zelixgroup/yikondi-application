import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInsuranceType } from 'app/shared/model/insurance-type.model';
import { InsuranceTypeService } from './insurance-type.service';

@Component({
  templateUrl: './insurance-type-delete-dialog.component.html'
})
export class InsuranceTypeDeleteDialogComponent {
  insuranceType: IInsuranceType;

  constructor(
    protected insuranceTypeService: InsuranceTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.insuranceTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'insuranceTypeListModification',
        content: 'Deleted an insuranceType'
      });
      this.activeModal.dismiss(true);
    });
  }
}
