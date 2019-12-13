import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { InsuranceTypeUpdateComponent } from 'app/entities/insurance-type/insurance-type-update.component';
import { InsuranceTypeService } from 'app/entities/insurance-type/insurance-type.service';
import { InsuranceType } from 'app/shared/model/insurance-type.model';

describe('Component Tests', () => {
  describe('InsuranceType Management Update Component', () => {
    let comp: InsuranceTypeUpdateComponent;
    let fixture: ComponentFixture<InsuranceTypeUpdateComponent>;
    let service: InsuranceTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [InsuranceTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InsuranceTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InsuranceTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InsuranceTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InsuranceType(123);
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
        const entity = new InsuranceType();
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
