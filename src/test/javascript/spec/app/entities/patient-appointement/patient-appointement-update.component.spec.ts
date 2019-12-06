import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PatientAppointementUpdateComponent } from 'app/entities/patient-appointement/patient-appointement-update.component';
import { PatientAppointementService } from 'app/entities/patient-appointement/patient-appointement.service';
import { PatientAppointement } from 'app/shared/model/patient-appointement.model';

describe('Component Tests', () => {
  describe('PatientAppointement Management Update Component', () => {
    let comp: PatientAppointementUpdateComponent;
    let fixture: ComponentFixture<PatientAppointementUpdateComponent>;
    let service: PatientAppointementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientAppointementUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PatientAppointementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatientAppointementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientAppointementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PatientAppointement(123);
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
        const entity = new PatientAppointement();
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
