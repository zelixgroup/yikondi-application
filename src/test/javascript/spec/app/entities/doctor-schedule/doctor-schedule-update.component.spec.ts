import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { DoctorScheduleUpdateComponent } from 'app/entities/doctor-schedule/doctor-schedule-update.component';
import { DoctorScheduleService } from 'app/entities/doctor-schedule/doctor-schedule.service';
import { DoctorSchedule } from 'app/shared/model/doctor-schedule.model';

describe('Component Tests', () => {
  describe('DoctorSchedule Management Update Component', () => {
    let comp: DoctorScheduleUpdateComponent;
    let fixture: ComponentFixture<DoctorScheduleUpdateComponent>;
    let service: DoctorScheduleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DoctorScheduleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DoctorScheduleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoctorScheduleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoctorScheduleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DoctorSchedule(123);
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
        const entity = new DoctorSchedule();
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
