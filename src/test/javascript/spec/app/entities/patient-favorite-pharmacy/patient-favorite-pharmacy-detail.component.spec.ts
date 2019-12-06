import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PatientFavoritePharmacyDetailComponent } from 'app/entities/patient-favorite-pharmacy/patient-favorite-pharmacy-detail.component';
import { PatientFavoritePharmacy } from 'app/shared/model/patient-favorite-pharmacy.model';

describe('Component Tests', () => {
  describe('PatientFavoritePharmacy Management Detail Component', () => {
    let comp: PatientFavoritePharmacyDetailComponent;
    let fixture: ComponentFixture<PatientFavoritePharmacyDetailComponent>;
    const route = ({ data: of({ patientFavoritePharmacy: new PatientFavoritePharmacy(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientFavoritePharmacyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PatientFavoritePharmacyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PatientFavoritePharmacyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.patientFavoritePharmacy).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
