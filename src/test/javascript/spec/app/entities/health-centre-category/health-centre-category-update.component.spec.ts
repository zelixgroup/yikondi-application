import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { HealthCentreCategoryUpdateComponent } from 'app/entities/health-centre-category/health-centre-category-update.component';
import { HealthCentreCategoryService } from 'app/entities/health-centre-category/health-centre-category.service';
import { HealthCentreCategory } from 'app/shared/model/health-centre-category.model';

describe('Component Tests', () => {
  describe('HealthCentreCategory Management Update Component', () => {
    let comp: HealthCentreCategoryUpdateComponent;
    let fixture: ComponentFixture<HealthCentreCategoryUpdateComponent>;
    let service: HealthCentreCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [HealthCentreCategoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(HealthCentreCategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HealthCentreCategoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HealthCentreCategoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new HealthCentreCategory(123);
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
        const entity = new HealthCentreCategory();
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
