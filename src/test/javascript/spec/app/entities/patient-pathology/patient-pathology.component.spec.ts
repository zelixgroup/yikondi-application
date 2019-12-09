import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { PatientPathologyComponent } from 'app/entities/patient-pathology/patient-pathology.component';
import { PatientPathologyService } from 'app/entities/patient-pathology/patient-pathology.service';
import { PatientPathology } from 'app/shared/model/patient-pathology.model';

describe('Component Tests', () => {
  describe('PatientPathology Management Component', () => {
    let comp: PatientPathologyComponent;
    let fixture: ComponentFixture<PatientPathologyComponent>;
    let service: PatientPathologyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientPathologyComponent],
        providers: []
      })
        .overrideTemplate(PatientPathologyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatientPathologyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientPathologyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PatientPathology(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.patientPathologies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
