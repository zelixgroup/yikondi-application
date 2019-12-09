import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { PatientLifeConstantComponent } from 'app/entities/patient-life-constant/patient-life-constant.component';
import { PatientLifeConstantService } from 'app/entities/patient-life-constant/patient-life-constant.service';
import { PatientLifeConstant } from 'app/shared/model/patient-life-constant.model';

describe('Component Tests', () => {
  describe('PatientLifeConstant Management Component', () => {
    let comp: PatientLifeConstantComponent;
    let fixture: ComponentFixture<PatientLifeConstantComponent>;
    let service: PatientLifeConstantService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientLifeConstantComponent],
        providers: []
      })
        .overrideTemplate(PatientLifeConstantComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatientLifeConstantComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientLifeConstantService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PatientLifeConstant(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.patientLifeConstants[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
