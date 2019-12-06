import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHealthCentre } from 'app/shared/model/health-centre.model';
import { HealthCentreService } from './health-centre.service';

@Component({
  templateUrl: './health-centre-delete-dialog.component.html'
})
export class HealthCentreDeleteDialogComponent {
  healthCentre: IHealthCentre;

  constructor(
    protected healthCentreService: HealthCentreService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.healthCentreService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'healthCentreListModification',
        content: 'Deleted an healthCentre'
      });
      this.activeModal.dismiss(true);
    });
  }
}
