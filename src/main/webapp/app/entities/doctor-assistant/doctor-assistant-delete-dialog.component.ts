import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDoctorAssistant } from 'app/shared/model/doctor-assistant.model';
import { DoctorAssistantService } from './doctor-assistant.service';

@Component({
  templateUrl: './doctor-assistant-delete-dialog.component.html'
})
export class DoctorAssistantDeleteDialogComponent {
  doctorAssistant: IDoctorAssistant;

  constructor(
    protected doctorAssistantService: DoctorAssistantService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.doctorAssistantService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'doctorAssistantListModification',
        content: 'Deleted an doctorAssistant'
      });
      this.activeModal.dismiss(true);
    });
  }
}
