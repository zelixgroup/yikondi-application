import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PathologyUpdateComponent } from 'app/entities/pathology/pathology-update.component';
import { PathologyService } from 'app/entities/pathology/pathology.service';
import { Pathology } from 'app/shared/model/pathology.model';

describe('Component Tests', () => {
  describe('Pathology Management Update Component', () => {
    let comp: PathologyUpdateComponent;
    let fixture: ComponentFixture<PathologyUpdateComponent>;
    let service: PathologyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PathologyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PathologyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PathologyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PathologyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pathology(123);
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
        const entity = new Pathology();
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
