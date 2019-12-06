import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PatientFavoritePharmacyUpdateComponent } from 'app/entities/patient-favorite-pharmacy/patient-favorite-pharmacy-update.component';
import { PatientFavoritePharmacyService } from 'app/entities/patient-favorite-pharmacy/patient-favorite-pharmacy.service';
import { PatientFavoritePharmacy } from 'app/shared/model/patient-favorite-pharmacy.model';

describe('Component Tests', () => {
  describe('PatientFavoritePharmacy Management Update Component', () => {
    let comp: PatientFavoritePharmacyUpdateComponent;
    let fixture: ComponentFixture<PatientFavoritePharmacyUpdateComponent>;
    let service: PatientFavoritePharmacyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientFavoritePharmacyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PatientFavoritePharmacyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatientFavoritePharmacyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientFavoritePharmacyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PatientFavoritePharmacy(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PatientFavoritePharmacy();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
