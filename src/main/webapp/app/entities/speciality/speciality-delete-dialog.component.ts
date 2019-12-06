import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpeciality } from 'app/shared/model/speciality.model';
import { SpecialityService } from './speciality.service';

@Component({
  templateUrl: './speciality-delete-dialog.component.html'
})
export class SpecialityDeleteDialogComponent {
  speciality: ISpeciality;

  constructor(
    protected specialityService: SpecialityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.specialityService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'specialityListModification',
        content: 'Deleted an speciality'
      });
      this.activeModal.dismiss(true);
    });
  }
}
