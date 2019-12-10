import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { PatientEmergencyNumberComponent } from 'app/entities/patient-emergency-number/patient-emergency-number.component';
import { PatientEmergencyNumberService } from 'app/entities/patient-emergency-number/patient-emergency-number.service';
import { PatientEmergencyNumber } from 'app/shared/model/patient-emergency-number.model';

describe('Component Tests', () => {
  describe('PatientEmergencyNumber Management Component', () => {
    let comp: PatientEmergencyNumberComponent;
    let fixture: ComponentFixture<PatientEmergencyNumberComponent>;
    let service: PatientEmergencyNumberService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientEmergencyNumberComponent],
        providers: []
      })
        .overrideTemplate(PatientEmergencyNumberComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatientEmergencyNumberComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientEmergencyNumberService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PatientEmergencyNumber(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.patientEmergencyNumbers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
