import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { AllergyUpdateComponent } from 'app/entities/allergy/allergy-update.component';
import { AllergyService } from 'app/entities/allergy/allergy.service';
import { Allergy } from 'app/shared/model/allergy.model';

describe('Component Tests', () => {
  describe('Allergy Management Update Component', () => {
    let comp: AllergyUpdateComponent;
    let fixture: ComponentFixture<AllergyUpdateComponent>;
    let service: AllergyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [AllergyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AllergyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AllergyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllergyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Allergy(123);
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
        const entity = new Allergy();
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
