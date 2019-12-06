import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { SpecialityUpdateComponent } from 'app/entities/speciality/speciality-update.component';
import { SpecialityService } from 'app/entities/speciality/speciality.service';
import { Speciality } from 'app/shared/model/speciality.model';

describe('Component Tests', () => {
  describe('Speciality Management Update Component', () => {
    let comp: SpecialityUpdateComponent;
    let fixture: ComponentFixture<SpecialityUpdateComponent>;
    let service: SpecialityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [SpecialityUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SpecialityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpecialityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpecialityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Speciality(123);
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
        const entity = new Speciality();
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
