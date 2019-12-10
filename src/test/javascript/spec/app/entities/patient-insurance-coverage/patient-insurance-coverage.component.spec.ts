import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { PatientInsuranceCoverageComponent } from 'app/entities/patient-insurance-coverage/patient-insurance-coverage.component';
import { PatientInsuranceCoverageService } from 'app/entities/patient-insurance-coverage/patient-insurance-coverage.service';
import { PatientInsuranceCoverage } from 'app/shared/model/patient-insurance-coverage.model';

describe('Component Tests', () => {
  describe('PatientInsuranceCoverage Management Component', () => {
    let comp: PatientInsuranceCoverageComponent;
    let fixture: ComponentFixture<PatientInsuranceCoverageComponent>;
    let service: PatientInsuranceCoverageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientInsuranceCoverageComponent],
        providers: []
      })
        .overrideTemplate(PatientInsuranceCoverageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatientInsuranceCoverageComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientInsuranceCoverageService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PatientInsuranceCoverage(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.patientInsuranceCoverages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
