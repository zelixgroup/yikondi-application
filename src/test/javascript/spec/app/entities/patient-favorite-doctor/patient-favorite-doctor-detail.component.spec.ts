import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PatientFavoriteDoctorDetailComponent } from 'app/entities/patient-favorite-doctor/patient-favorite-doctor-detail.component';
import { PatientFavoriteDoctor } from 'app/shared/model/patient-favorite-doctor.model';

describe('Component Tests', () => {
  describe('PatientFavoriteDoctor Management Detail Component', () => {
    let comp: PatientFavoriteDoctorDetailComponent;
    let fixture: ComponentFixture<PatientFavoriteDoctorDetailComponent>;
    const route = ({ data: of({ patientFavoriteDoctor: new PatientFavoriteDoctor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientFavoriteDoctorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PatientFavoriteDoctorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PatientFavoriteDoctorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.patientFavoriteDoctor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
