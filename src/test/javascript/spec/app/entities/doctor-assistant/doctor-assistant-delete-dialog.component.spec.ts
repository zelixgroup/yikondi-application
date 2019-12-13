import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { DoctorAssistantDeleteDialogComponent } from 'app/entities/doctor-assistant/doctor-assistant-delete-dialog.component';
import { DoctorAssistantService } from 'app/entities/doctor-assistant/doctor-assistant.service';

describe('Component Tests', () => {
  describe('DoctorAssistant Management Delete Component', () => {
    let comp: DoctorAssistantDeleteDialogComponent;
    let fixture: ComponentFixture<DoctorAssistantDeleteDialogComponent>;
    let service: DoctorAssistantService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DoctorAssistantDeleteDialogComponent]
      })
        .overrideTemplate(DoctorAssistantDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoctorAssistantDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoctorAssistantService);
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
