import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { HealthCentreDoctorDeleteDialogComponent } from 'app/entities/health-centre-doctor/health-centre-doctor-delete-dialog.component';
import { HealthCentreDoctorService } from 'app/entities/health-centre-doctor/health-centre-doctor.service';

describe('Component Tests', () => {
  describe('HealthCentreDoctor Management Delete Component', () => {
    let comp: HealthCentreDoctorDeleteDialogComponent;
    let fixture: ComponentFixture<HealthCentreDoctorDeleteDialogComponent>;
    let service: HealthCentreDoctorService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [HealthCentreDoctorDeleteDialogComponent]
      })
        .overrideTemplate(HealthCentreDoctorDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HealthCentreDoctorDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HealthCentreDoctorService);
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
