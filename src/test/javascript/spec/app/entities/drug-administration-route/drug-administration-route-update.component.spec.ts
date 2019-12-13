import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { DrugAdministrationRouteUpdateComponent } from 'app/entities/drug-administration-route/drug-administration-route-update.component';
import { DrugAdministrationRouteService } from 'app/entities/drug-administration-route/drug-administration-route.service';
import { DrugAdministrationRoute } from 'app/shared/model/drug-administration-route.model';

describe('Component Tests', () => {
  describe('DrugAdministrationRoute Management Update Component', () => {
    let comp: DrugAdministrationRouteUpdateComponent;
    let fixture: ComponentFixture<DrugAdministrationRouteUpdateComponent>;
    let service: DrugAdministrationRouteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DrugAdministrationRouteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DrugAdministrationRouteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DrugAdministrationRouteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DrugAdministrationRouteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DrugAdministrationRoute(123);
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
        const entity = new DrugAdministrationRoute();
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
