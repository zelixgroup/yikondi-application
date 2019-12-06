import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { DoctorWorkingSlotUpdateComponent } from 'app/entities/doctor-working-slot/doctor-working-slot-update.component';
import { DoctorWorkingSlotService } from 'app/entities/doctor-working-slot/doctor-working-slot.service';
import { DoctorWorkingSlot } from 'app/shared/model/doctor-working-slot.model';

describe('Component Tests', () => {
  describe('DoctorWorkingSlot Management Update Component', () => {
    let comp: DoctorWorkingSlotUpdateComponent;
    let fixture: ComponentFixture<DoctorWorkingSlotUpdateComponent>;
    let service: DoctorWorkingSlotService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DoctorWorkingSlotUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DoctorWorkingSlotUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoctorWorkingSlotUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoctorWorkingSlotService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DoctorWorkingSlot(123);
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
        const entity = new DoctorWorkingSlot();
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
