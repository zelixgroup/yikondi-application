import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { DoctorAssistantUpdateComponent } from 'app/entities/doctor-assistant/doctor-assistant-update.component';
import { DoctorAssistantService } from 'app/entities/doctor-assistant/doctor-assistant.service';
import { DoctorAssistant } from 'app/shared/model/doctor-assistant.model';

describe('Component Tests', () => {
  describe('DoctorAssistant Management Update Component', () => {
    let comp: DoctorAssistantUpdateComponent;
    let fixture: ComponentFixture<DoctorAssistantUpdateComponent>;
    let service: DoctorAssistantService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DoctorAssistantUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DoctorAssistantUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoctorAssistantUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoctorAssistantService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DoctorAssistant(123);
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
        const entity = new DoctorAssistant();
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
