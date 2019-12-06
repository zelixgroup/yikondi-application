import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PharmacyAllNightPlanningUpdateComponent } from 'app/entities/pharmacy-all-night-planning/pharmacy-all-night-planning-update.component';
import { PharmacyAllNightPlanningService } from 'app/entities/pharmacy-all-night-planning/pharmacy-all-night-planning.service';
import { PharmacyAllNightPlanning } from 'app/shared/model/pharmacy-all-night-planning.model';

describe('Component Tests', () => {
  describe('PharmacyAllNightPlanning Management Update Component', () => {
    let comp: PharmacyAllNightPlanningUpdateComponent;
    let fixture: ComponentFixture<PharmacyAllNightPlanningUpdateComponent>;
    let service: PharmacyAllNightPlanningService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PharmacyAllNightPlanningUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PharmacyAllNightPlanningUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PharmacyAllNightPlanningUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PharmacyAllNightPlanningService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PharmacyAllNightPlanning(123);
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
        const entity = new PharmacyAllNightPlanning();
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
