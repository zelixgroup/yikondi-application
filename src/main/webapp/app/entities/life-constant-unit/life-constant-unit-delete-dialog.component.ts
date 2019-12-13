import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILifeConstantUnit } from 'app/shared/model/life-constant-unit.model';
import { LifeConstantUnitService } from './life-constant-unit.service';

@Component({
  templateUrl: './life-constant-unit-delete-dialog.component.html'
})
export class LifeConstantUnitDeleteDialogComponent {
  lifeConstantUnit: ILifeConstantUnit;

  constructor(
    protected lifeConstantUnitService: LifeConstantUnitService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.lifeConstantUnitService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'lifeConstantUnitListModification',
        content: 'Deleted an lifeConstantUnit'
      });
      this.activeModal.dismiss(true);
    });
  }
}
