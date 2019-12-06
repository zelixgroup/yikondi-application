import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { PatientAppointementComponent } from 'app/entities/patient-appointement/patient-appointement.component';
import { PatientAppointementService } from 'app/entities/patient-appointement/patient-appointement.service';
import { PatientAppointement } from 'app/shared/model/patient-appointement.model';

describe('Component Tests', () => {
  describe('PatientAppointement Management Component', () => {
    let comp: PatientAppointementComponent;
    let fixture: ComponentFixture<PatientAppointementComponent>;
    let service: PatientAppointementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientAppointementComponent],
        providers: []
      })
        .overrideTemplate(PatientAppointementComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatientAppointementComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientAppointementService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PatientAppointement(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.patientAppointements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
