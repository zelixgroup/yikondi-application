import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PatientAllergyUpdateComponent } from 'app/entities/patient-allergy/patient-allergy-update.component';
import { PatientAllergyService } from 'app/entities/patient-allergy/patient-allergy.service';
import { PatientAllergy } from 'app/shared/model/patient-allergy.model';

describe('Component Tests', () => {
  describe('PatientAllergy Management Update Component', () => {
    let comp: PatientAllergyUpdateComponent;
    let fixture: ComponentFixture<PatientAllergyUpdateComponent>;
    let service: PatientAllergyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientAllergyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PatientAllergyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatientAllergyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientAllergyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PatientAllergy(123);
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
        const entity = new PatientAllergy();
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
