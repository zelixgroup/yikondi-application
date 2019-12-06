import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PatientAppointementDetailComponent } from 'app/entities/patient-appointement/patient-appointement-detail.component';
import { PatientAppointement } from 'app/shared/model/patient-appointement.model';

describe('Component Tests', () => {
  describe('PatientAppointement Management Detail Component', () => {
    let comp: PatientAppointementDetailComponent;
    let fixture: ComponentFixture<PatientAppointementDetailComponent>;
    const route = ({ data: of({ patientAppointement: new PatientAppointement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientAppointementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PatientAppointementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PatientAppointementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.patientAppointement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
