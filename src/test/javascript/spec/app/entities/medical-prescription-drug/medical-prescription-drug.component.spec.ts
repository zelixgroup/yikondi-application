import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { MedicalPrescriptionDrugComponent } from 'app/entities/medical-prescription-drug/medical-prescription-drug.component';
import { MedicalPrescriptionDrugService } from 'app/entities/medical-prescription-drug/medical-prescription-drug.service';
import { MedicalPrescriptionDrug } from 'app/shared/model/medical-prescription-drug.model';

describe('Component Tests', () => {
  describe('MedicalPrescriptionDrug Management Component', () => {
    let comp: MedicalPrescriptionDrugComponent;
    let fixture: ComponentFixture<MedicalPrescriptionDrugComponent>;
    let service: MedicalPrescriptionDrugService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [MedicalPrescriptionDrugComponent],
        providers: []
      })
        .overrideTemplate(MedicalPrescriptionDrugComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicalPrescriptionDrugComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalPrescriptionDrugService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MedicalPrescriptionDrug(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.medicalPrescriptionDrugs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
