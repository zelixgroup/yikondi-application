import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDrugAdministrationRoute } from 'app/shared/model/drug-administration-route.model';
import { DrugAdministrationRouteService } from './drug-administration-route.service';

@Component({
  templateUrl: './drug-administration-route-delete-dialog.component.html'
})
export class DrugAdministrationRouteDeleteDialogComponent {
  drugAdministrationRoute: IDrugAdministrationRoute;

  constructor(
    protected drugAdministrationRouteService: DrugAdministrationRouteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.drugAdministrationRouteService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'drugAdministrationRouteListModification',
        content: 'Deleted an drugAdministrationRoute'
      });
      this.activeModal.dismiss(true);
    });
  }
}
