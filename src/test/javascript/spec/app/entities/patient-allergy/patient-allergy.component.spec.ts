import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { PatientAllergyComponent } from 'app/entities/patient-allergy/patient-allergy.component';
import { PatientAllergyService } from 'app/entities/patient-allergy/patient-allergy.service';
import { PatientAllergy } from 'app/shared/model/patient-allergy.model';

describe('Component Tests', () => {
  describe('PatientAllergy Management Component', () => {
    let comp: PatientAllergyComponent;
    let fixture: ComponentFixture<PatientAllergyComponent>;
    let service: PatientAllergyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientAllergyComponent],
        providers: []
      })
        .overrideTemplate(PatientAllergyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatientAllergyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientAllergyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PatientAllergy(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.patientAllergies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
