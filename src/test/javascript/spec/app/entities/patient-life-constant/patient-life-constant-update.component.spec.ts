import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PatientLifeConstantUpdateComponent } from 'app/entities/patient-life-constant/patient-life-constant-update.component';
import { PatientLifeConstantService } from 'app/entities/patient-life-constant/patient-life-constant.service';
import { PatientLifeConstant } from 'app/shared/model/patient-life-constant.model';

describe('Component Tests', () => {
  describe('PatientLifeConstant Management Update Component', () => {
    let comp: PatientLifeConstantUpdateComponent;
    let fixture: ComponentFixture<PatientLifeConstantUpdateComponent>;
    let service: PatientLifeConstantService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientLifeConstantUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PatientLifeConstantUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatientLifeConstantUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientLifeConstantService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PatientLifeConstant(123);
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
        const entity = new PatientLifeConstant();
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
