import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { PatientFavoritePharmacyComponent } from 'app/entities/patient-favorite-pharmacy/patient-favorite-pharmacy.component';
import { PatientFavoritePharmacyService } from 'app/entities/patient-favorite-pharmacy/patient-favorite-pharmacy.service';
import { PatientFavoritePharmacy } from 'app/shared/model/patient-favorite-pharmacy.model';

describe('Component Tests', () => {
  describe('PatientFavoritePharmacy Management Component', () => {
    let comp: PatientFavoritePharmacyComponent;
    let fixture: ComponentFixture<PatientFavoritePharmacyComponent>;
    let service: PatientFavoritePharmacyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientFavoritePharmacyComponent],
        providers: []
      })
        .overrideTemplate(PatientFavoritePharmacyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatientFavoritePharmacyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientFavoritePharmacyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PatientFavoritePharmacy(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.patientFavoritePharmacies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
