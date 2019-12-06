import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { PatientFavoriteDoctorDeleteDialogComponent } from 'app/entities/patient-favorite-doctor/patient-favorite-doctor-delete-dialog.component';
import { PatientFavoriteDoctorService } from 'app/entities/patient-favorite-doctor/patient-favorite-doctor.service';

describe('Component Tests', () => {
  describe('PatientFavoriteDoctor Management Delete Component', () => {
    let comp: PatientFavoriteDoctorDeleteDialogComponent;
    let fixture: ComponentFixture<PatientFavoriteDoctorDeleteDialogComponent>;
    let service: PatientFavoriteDoctorService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientFavoriteDoctorDeleteDialogComponent]
      })
        .overrideTemplate(PatientFavoriteDoctorDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PatientFavoriteDoctorDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientFavoriteDoctorService);
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
