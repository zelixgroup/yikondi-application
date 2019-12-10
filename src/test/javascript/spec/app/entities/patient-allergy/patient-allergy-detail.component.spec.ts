import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PatientAllergyDetailComponent } from 'app/entities/patient-allergy/patient-allergy-detail.component';
import { PatientAllergy } from 'app/shared/model/patient-allergy.model';

describe('Component Tests', () => {
  describe('PatientAllergy Management Detail Component', () => {
    let comp: PatientAllergyDetailComponent;
    let fixture: ComponentFixture<PatientAllergyDetailComponent>;
    const route = ({ data: of({ patientAllergy: new PatientAllergy(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientAllergyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PatientAllergyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PatientAllergyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.patientAllergy).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
