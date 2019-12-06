import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { DoctorWorkingSlotDeleteDialogComponent } from 'app/entities/doctor-working-slot/doctor-working-slot-delete-dialog.component';
import { DoctorWorkingSlotService } from 'app/entities/doctor-working-slot/doctor-working-slot.service';

describe('Component Tests', () => {
  describe('DoctorWorkingSlot Management Delete Component', () => {
    let comp: DoctorWorkingSlotDeleteDialogComponent;
    let fixture: ComponentFixture<DoctorWorkingSlotDeleteDialogComponent>;
    let service: DoctorWorkingSlotService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DoctorWorkingSlotDeleteDialogComponent]
      })
        .overrideTemplate(DoctorWorkingSlotDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoctorWorkingSlotDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoctorWorkingSlotService);
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
