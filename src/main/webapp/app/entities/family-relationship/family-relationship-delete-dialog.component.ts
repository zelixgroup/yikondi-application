import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFamilyRelationship } from 'app/shared/model/family-relationship.model';
import { FamilyRelationshipService } from './family-relationship.service';

@Component({
  templateUrl: './family-relationship-delete-dialog.component.html'
})
export class FamilyRelationshipDeleteDialogComponent {
  familyRelationship: IFamilyRelationship;

  constructor(
    protected familyRelationshipService: FamilyRelationshipService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.familyRelationshipService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'familyRelationshipListModification',
        content: 'Deleted an familyRelationship'
      });
      this.activeModal.dismiss(true);
    });
  }
}
