import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { HealthCentreUpdateComponent } from 'app/entities/health-centre/health-centre-update.component';
import { HealthCentreService } from 'app/entities/health-centre/health-centre.service';
import { HealthCentre } from 'app/shared/model/health-centre.model';

describe('Component Tests', () => {
  describe('HealthCentre Management Update Component', () => {
    let comp: HealthCentreUpdateComponent;
    let fixture: ComponentFixture<HealthCentreUpdateComponent>;
    let service: HealthCentreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [HealthCentreUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(HealthCentreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HealthCentreUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HealthCentreService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new HealthCentre(123);
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
        const entity = new HealthCentre();
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
