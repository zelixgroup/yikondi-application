import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { LifeConstantUnitUpdateComponent } from 'app/entities/life-constant-unit/life-constant-unit-update.component';
import { LifeConstantUnitService } from 'app/entities/life-constant-unit/life-constant-unit.service';
import { LifeConstantUnit } from 'app/shared/model/life-constant-unit.model';

describe('Component Tests', () => {
  describe('LifeConstantUnit Management Update Component', () => {
    let comp: LifeConstantUnitUpdateComponent;
    let fixture: ComponentFixture<LifeConstantUnitUpdateComponent>;
    let service: LifeConstantUnitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [LifeConstantUnitUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LifeConstantUnitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LifeConstantUnitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LifeConstantUnitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LifeConstantUnit(123);
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
        const entity = new LifeConstantUnit();
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
