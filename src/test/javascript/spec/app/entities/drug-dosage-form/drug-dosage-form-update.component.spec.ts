import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { DrugDosageFormUpdateComponent } from 'app/entities/drug-dosage-form/drug-dosage-form-update.component';
import { DrugDosageFormService } from 'app/entities/drug-dosage-form/drug-dosage-form.service';
import { DrugDosageForm } from 'app/shared/model/drug-dosage-form.model';

describe('Component Tests', () => {
  describe('DrugDosageForm Management Update Component', () => {
    let comp: DrugDosageFormUpdateComponent;
    let fixture: ComponentFixture<DrugDosageFormUpdateComponent>;
    let service: DrugDosageFormService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DrugDosageFormUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DrugDosageFormUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DrugDosageFormUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DrugDosageFormService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DrugDosageForm(123);
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
        const entity = new DrugDosageForm();
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
