import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILifeConstant } from 'app/shared/model/life-constant.model';
import { LifeConstantService } from './life-constant.service';

@Component({
  templateUrl: './life-constant-delete-dialog.component.html'
})
export class LifeConstantDeleteDialogComponent {
  lifeConstant: ILifeConstant;

  constructor(
    protected lifeConstantService: LifeConstantService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.lifeConstantService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'lifeConstantListModification',
        content: 'Deleted an lifeConstant'
      });
      this.activeModal.dismiss(true);
    });
  }
}
