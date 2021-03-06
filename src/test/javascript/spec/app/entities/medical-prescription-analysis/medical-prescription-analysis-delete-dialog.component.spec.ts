import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { MedicalPrescriptionAnalysisDeleteDialogComponent } from 'app/entities/medical-prescription-analysis/medical-prescription-analysis-delete-dialog.component';
import { MedicalPrescriptionAnalysisService } from 'app/entities/medical-prescription-analysis/medical-prescription-analysis.service';

describe('Component Tests', () => {
  describe('MedicalPrescriptionAnalysis Management Delete Component', () => {
    let comp: MedicalPrescriptionAnalysisDeleteDialogComponent;
    let fixture: ComponentFixture<MedicalPrescriptionAnalysisDeleteDialogComponent>;
    let service: MedicalPrescriptionAnalysisService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [MedicalPrescriptionAnalysisDeleteDialogComponent]
      })
        .overrideTemplate(MedicalPrescriptionAnalysisDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicalPrescriptionAnalysisDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalPrescriptionAnalysisService);
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
