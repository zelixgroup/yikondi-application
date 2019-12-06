import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { DoctorScheduleDeleteDialogComponent } from 'app/entities/doctor-schedule/doctor-schedule-delete-dialog.component';
import { DoctorScheduleService } from 'app/entities/doctor-schedule/doctor-schedule.service';

describe('Component Tests', () => {
  describe('DoctorSchedule Management Delete Component', () => {
    let comp: DoctorScheduleDeleteDialogComponent;
    let fixture: ComponentFixture<DoctorScheduleDeleteDialogComponent>;
    let service: DoctorScheduleService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DoctorScheduleDeleteDialogComponent]
      })
        .overrideTemplate(DoctorScheduleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoctorScheduleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoctorScheduleService);
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
