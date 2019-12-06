import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { HealthCentreDoctorDetailComponent } from 'app/entities/health-centre-doctor/health-centre-doctor-detail.component';
import { HealthCentreDoctor } from 'app/shared/model/health-centre-doctor.model';

describe('Component Tests', () => {
  describe('HealthCentreDoctor Management Detail Component', () => {
    let comp: HealthCentreDoctorDetailComponent;
    let fixture: ComponentFixture<HealthCentreDoctorDetailComponent>;
    const route = ({ data: of({ healthCentreDoctor: new HealthCentreDoctor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [HealthCentreDoctorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HealthCentreDoctorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HealthCentreDoctorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.healthCentreDoctor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
