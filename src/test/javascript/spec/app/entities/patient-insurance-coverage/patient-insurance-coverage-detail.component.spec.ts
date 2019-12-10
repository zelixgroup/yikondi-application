import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PatientInsuranceCoverageDetailComponent } from 'app/entities/patient-insurance-coverage/patient-insurance-coverage-detail.component';
import { PatientInsuranceCoverage } from 'app/shared/model/patient-insurance-coverage.model';

describe('Component Tests', () => {
  describe('PatientInsuranceCoverage Management Detail Component', () => {
    let comp: PatientInsuranceCoverageDetailComponent;
    let fixture: ComponentFixture<PatientInsuranceCoverageDetailComponent>;
    const route = ({ data: of({ patientInsuranceCoverage: new PatientInsuranceCoverage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientInsuranceCoverageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PatientInsuranceCoverageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PatientInsuranceCoverageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.patientInsuranceCoverage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
