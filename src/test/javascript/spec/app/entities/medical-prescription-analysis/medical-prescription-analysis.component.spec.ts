import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { MedicalPrescriptionAnalysisComponent } from 'app/entities/medical-prescription-analysis/medical-prescription-analysis.component';
import { MedicalPrescriptionAnalysisService } from 'app/entities/medical-prescription-analysis/medical-prescription-analysis.service';
import { MedicalPrescriptionAnalysis } from 'app/shared/model/medical-prescription-analysis.model';

describe('Component Tests', () => {
  describe('MedicalPrescriptionAnalysis Management Component', () => {
    let comp: MedicalPrescriptionAnalysisComponent;
    let fixture: ComponentFixture<MedicalPrescriptionAnalysisComponent>;
    let service: MedicalPrescriptionAnalysisService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [MedicalPrescriptionAnalysisComponent],
        providers: []
      })
        .overrideTemplate(MedicalPrescriptionAnalysisComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicalPrescriptionAnalysisComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalPrescriptionAnalysisService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MedicalPrescriptionAnalysis(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.medicalPrescriptionAnalyses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
