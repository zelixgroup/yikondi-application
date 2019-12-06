import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { PatientFavoriteDoctorComponent } from 'app/entities/patient-favorite-doctor/patient-favorite-doctor.component';
import { PatientFavoriteDoctorService } from 'app/entities/patient-favorite-doctor/patient-favorite-doctor.service';
import { PatientFavoriteDoctor } from 'app/shared/model/patient-favorite-doctor.model';

describe('Component Tests', () => {
  describe('PatientFavoriteDoctor Management Component', () => {
    let comp: PatientFavoriteDoctorComponent;
    let fixture: ComponentFixture<PatientFavoriteDoctorComponent>;
    let service: PatientFavoriteDoctorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientFavoriteDoctorComponent],
        providers: []
      })
        .overrideTemplate(PatientFavoriteDoctorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatientFavoriteDoctorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientFavoriteDoctorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PatientFavoriteDoctor(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.patientFavoriteDoctors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
