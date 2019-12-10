import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { LifeConstantUpdateComponent } from 'app/entities/life-constant/life-constant-update.component';
import { LifeConstantService } from 'app/entities/life-constant/life-constant.service';
import { LifeConstant } from 'app/shared/model/life-constant.model';

describe('Component Tests', () => {
  describe('LifeConstant Management Update Component', () => {
    let comp: LifeConstantUpdateComponent;
    let fixture: ComponentFixture<LifeConstantUpdateComponent>;
    let service: LifeConstantService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [LifeConstantUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LifeConstantUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LifeConstantUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LifeConstantService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LifeConstant(123);
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
        const entity = new LifeConstant();
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
