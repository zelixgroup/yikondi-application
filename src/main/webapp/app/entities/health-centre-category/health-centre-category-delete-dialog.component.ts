import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHealthCentreCategory } from 'app/shared/model/health-centre-category.model';
import { HealthCentreCategoryService } from './health-centre-category.service';

@Component({
  templateUrl: './health-centre-category-delete-dialog.component.html'
})
export class HealthCentreCategoryDeleteDialogComponent {
  healthCentreCategory: IHealthCentreCategory;

  constructor(
    protected healthCentreCategoryService: HealthCentreCategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.healthCentreCategoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'healthCentreCategoryListModification',
        content: 'Deleted an healthCentreCategory'
      });
      this.activeModal.dismiss(true);
    });
  }
}
