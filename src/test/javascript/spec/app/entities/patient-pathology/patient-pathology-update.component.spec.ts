import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PatientPathologyUpdateComponent } from 'app/entities/patient-pathology/patient-pathology-update.component';
import { PatientPathologyService } from 'app/entities/patient-pathology/patient-pathology.service';
import { PatientPathology } from 'app/shared/model/patient-pathology.model';

describe('Component Tests', () => {
  describe('PatientPathology Management Update Component', () => {
    let comp: PatientPathologyUpdateComponent;
    let fixture: ComponentFixture<PatientPathologyUpdateComponent>;
    let service: PatientPathologyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientPathologyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PatientPathologyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatientPathologyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientPathologyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PatientPathology(123);
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
        const entity = new PatientPathology();
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
