import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { PatientPathologyDeleteDialogComponent } from 'app/entities/patient-pathology/patient-pathology-delete-dialog.component';
import { PatientPathologyService } from 'app/entities/patient-pathology/patient-pathology.service';

describe('Component Tests', () => {
  describe('PatientPathology Management Delete Component', () => {
    let comp: PatientPathologyDeleteDialogComponent;
    let fixture: ComponentFixture<PatientPathologyDeleteDialogComponent>;
    let service: PatientPathologyService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientPathologyDeleteDialogComponent]
      })
        .overrideTemplate(PatientPathologyDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PatientPathologyDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientPathologyService);
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
