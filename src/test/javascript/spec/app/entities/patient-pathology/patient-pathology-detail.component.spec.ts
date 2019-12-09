import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PatientPathologyDetailComponent } from 'app/entities/patient-pathology/patient-pathology-detail.component';
import { PatientPathology } from 'app/shared/model/patient-pathology.model';

describe('Component Tests', () => {
  describe('PatientPathology Management Detail Component', () => {
    let comp: PatientPathologyDetailComponent;
    let fixture: ComponentFixture<PatientPathologyDetailComponent>;
    const route = ({ data: of({ patientPathology: new PatientPathology(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientPathologyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PatientPathologyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PatientPathologyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.patientPathology).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
