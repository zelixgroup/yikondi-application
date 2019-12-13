import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { MedicalPrescriptionDrugDeleteDialogComponent } from 'app/entities/medical-prescription-drug/medical-prescription-drug-delete-dialog.component';
import { MedicalPrescriptionDrugService } from 'app/entities/medical-prescription-drug/medical-prescription-drug.service';

describe('Component Tests', () => {
  describe('MedicalPrescriptionDrug Management Delete Component', () => {
    let comp: MedicalPrescriptionDrugDeleteDialogComponent;
    let fixture: ComponentFixture<MedicalPrescriptionDrugDeleteDialogComponent>;
    let service: MedicalPrescriptionDrugService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [MedicalPrescriptionDrugDeleteDialogComponent]
      })
        .overrideTemplate(MedicalPrescriptionDrugDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicalPrescriptionDrugDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalPrescriptionDrugService);
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
