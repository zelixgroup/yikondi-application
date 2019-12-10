import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PatientEmergencyNumberUpdateComponent } from 'app/entities/patient-emergency-number/patient-emergency-number-update.component';
import { PatientEmergencyNumberService } from 'app/entities/patient-emergency-number/patient-emergency-number.service';
import { PatientEmergencyNumber } from 'app/shared/model/patient-emergency-number.model';

describe('Component Tests', () => {
  describe('PatientEmergencyNumber Management Update Component', () => {
    let comp: PatientEmergencyNumberUpdateComponent;
    let fixture: ComponentFixture<PatientEmergencyNumberUpdateComponent>;
    let service: PatientEmergencyNumberService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientEmergencyNumberUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PatientEmergencyNumberUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatientEmergencyNumberUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientEmergencyNumberService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PatientEmergencyNumber(123);
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
        const entity = new PatientEmergencyNumber();
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
