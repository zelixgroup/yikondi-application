import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { PatientInsuranceCoverageDeleteDialogComponent } from 'app/entities/patient-insurance-coverage/patient-insurance-coverage-delete-dialog.component';
import { PatientInsuranceCoverageService } from 'app/entities/patient-insurance-coverage/patient-insurance-coverage.service';

describe('Component Tests', () => {
  describe('PatientInsuranceCoverage Management Delete Component', () => {
    let comp: PatientInsuranceCoverageDeleteDialogComponent;
    let fixture: ComponentFixture<PatientInsuranceCoverageDeleteDialogComponent>;
    let service: PatientInsuranceCoverageService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientInsuranceCoverageDeleteDialogComponent]
      })
        .overrideTemplate(PatientInsuranceCoverageDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PatientInsuranceCoverageDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientInsuranceCoverageService);
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
