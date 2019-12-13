import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { DrugUpdateComponent } from 'app/entities/drug/drug-update.component';
import { DrugService } from 'app/entities/drug/drug.service';
import { Drug } from 'app/shared/model/drug.model';

describe('Component Tests', () => {
  describe('Drug Management Update Component', () => {
    let comp: DrugUpdateComponent;
    let fixture: ComponentFixture<DrugUpdateComponent>;
    let service: DrugService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DrugUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DrugUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DrugUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DrugService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Drug(123);
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
        const entity = new Drug();
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
