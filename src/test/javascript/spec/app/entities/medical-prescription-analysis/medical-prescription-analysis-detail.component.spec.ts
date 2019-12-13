import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { MedicalPrescriptionAnalysisDetailComponent } from 'app/entities/medical-prescription-analysis/medical-prescription-analysis-detail.component';
import { MedicalPrescriptionAnalysis } from 'app/shared/model/medical-prescription-analysis.model';

describe('Component Tests', () => {
  describe('MedicalPrescriptionAnalysis Management Detail Component', () => {
    let comp: MedicalPrescriptionAnalysisDetailComponent;
    let fixture: ComponentFixture<MedicalPrescriptionAnalysisDetailComponent>;
    const route = ({ data: of({ medicalPrescriptionAnalysis: new MedicalPrescriptionAnalysis(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [MedicalPrescriptionAnalysisDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MedicalPrescriptionAnalysisDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicalPrescriptionAnalysisDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.medicalPrescriptionAnalysis).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
