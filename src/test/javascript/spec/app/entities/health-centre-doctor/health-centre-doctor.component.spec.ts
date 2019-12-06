import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { HealthCentreDoctorComponent } from 'app/entities/health-centre-doctor/health-centre-doctor.component';
import { HealthCentreDoctorService } from 'app/entities/health-centre-doctor/health-centre-doctor.service';
import { HealthCentreDoctor } from 'app/shared/model/health-centre-doctor.model';

describe('Component Tests', () => {
  describe('HealthCentreDoctor Management Component', () => {
    let comp: HealthCentreDoctorComponent;
    let fixture: ComponentFixture<HealthCentreDoctorComponent>;
    let service: HealthCentreDoctorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [HealthCentreDoctorComponent],
        providers: []
      })
        .overrideTemplate(HealthCentreDoctorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HealthCentreDoctorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HealthCentreDoctorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new HealthCentreDoctor(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.healthCentreDoctors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
