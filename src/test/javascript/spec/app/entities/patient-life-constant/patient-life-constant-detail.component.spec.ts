import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PatientLifeConstantDetailComponent } from 'app/entities/patient-life-constant/patient-life-constant-detail.component';
import { PatientLifeConstant } from 'app/shared/model/patient-life-constant.model';

describe('Component Tests', () => {
  describe('PatientLifeConstant Management Detail Component', () => {
    let comp: PatientLifeConstantDetailComponent;
    let fixture: ComponentFixture<PatientLifeConstantDetailComponent>;
    const route = ({ data: of({ patientLifeConstant: new PatientLifeConstant(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientLifeConstantDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PatientLifeConstantDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PatientLifeConstantDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.patientLifeConstant).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
