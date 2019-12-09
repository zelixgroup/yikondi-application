import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPathology } from 'app/shared/model/pathology.model';
import { PathologyService } from './pathology.service';

@Component({
  templateUrl: './pathology-delete-dialog.component.html'
})
export class PathologyDeleteDialogComponent {
  pathology: IPathology;

  constructor(protected pathologyService: PathologyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pathologyService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'pathologyListModification',
        content: 'Deleted an pathology'
      });
      this.activeModal.dismiss(true);
    });
  }
}
