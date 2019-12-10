import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { EmergencyAmbulanceDeleteDialogComponent } from 'app/entities/emergency-ambulance/emergency-ambulance-delete-dialog.component';
import { EmergencyAmbulanceService } from 'app/entities/emergency-ambulance/emergency-ambulance.service';

describe('Component Tests', () => {
  describe('EmergencyAmbulance Management Delete Component', () => {
    let comp: EmergencyAmbulanceDeleteDialogComponent;
    let fixture: ComponentFixture<EmergencyAmbulanceDeleteDialogComponent>;
    let service: EmergencyAmbulanceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [EmergencyAmbulanceDeleteDialogComponent]
      })
        .overrideTemplate(EmergencyAmbulanceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmergencyAmbulanceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmergencyAmbulanceService);
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
