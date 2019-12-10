import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PatientEmergencyNumberDetailComponent } from 'app/entities/patient-emergency-number/patient-emergency-number-detail.component';
import { PatientEmergencyNumber } from 'app/shared/model/patient-emergency-number.model';

describe('Component Tests', () => {
  describe('PatientEmergencyNumber Management Detail Component', () => {
    let comp: PatientEmergencyNumberDetailComponent;
    let fixture: ComponentFixture<PatientEmergencyNumberDetailComponent>;
    const route = ({ data: of({ patientEmergencyNumber: new PatientEmergencyNumber(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientEmergencyNumberDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PatientEmergencyNumberDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PatientEmergencyNumberDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.patientEmergencyNumber).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
