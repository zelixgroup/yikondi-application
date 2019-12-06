import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { PatientAppointementDeleteDialogComponent } from 'app/entities/patient-appointement/patient-appointement-delete-dialog.component';
import { PatientAppointementService } from 'app/entities/patient-appointement/patient-appointement.service';

describe('Component Tests', () => {
  describe('PatientAppointement Management Delete Component', () => {
    let comp: PatientAppointementDeleteDialogComponent;
    let fixture: ComponentFixture<PatientAppointementDeleteDialogComponent>;
    let service: PatientAppointementService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientAppointementDeleteDialogComponent]
      })
        .overrideTemplate(PatientAppointementDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PatientAppointementDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientAppointementService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
