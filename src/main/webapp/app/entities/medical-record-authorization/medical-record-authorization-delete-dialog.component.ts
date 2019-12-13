import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMedicalRecordAuthorization } from 'app/shared/model/medical-record-authorization.model';
import { MedicalRecordAuthorizationService } from './medical-record-authorization.service';

@Component({
  templateUrl: './medical-record-authorization-delete-dialog.component.html'
})
export class MedicalRecordAuthorizationDeleteDialogComponent {
  medicalRecordAuthorization: IMedicalRecordAuthorization;

  constructor(
    protected medicalRecordAuthorizationService: MedicalRecordAuthorizationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.medicalRecordAuthorizationService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'medicalRecordAuthorizationListModification',
        content: 'Deleted an medicalRecordAuthorization'
      });
      this.activeModal.dismiss(true);
    });
  }
}
