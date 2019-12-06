import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { DoctorScheduleComponent } from 'app/entities/doctor-schedule/doctor-schedule.component';
import { DoctorScheduleService } from 'app/entities/doctor-schedule/doctor-schedule.service';
import { DoctorSchedule } from 'app/shared/model/doctor-schedule.model';

describe('Component Tests', () => {
  describe('DoctorSchedule Management Component', () => {
    let comp: DoctorScheduleComponent;
    let fixture: ComponentFixture<DoctorScheduleComponent>;
    let service: DoctorScheduleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DoctorScheduleComponent],
        providers: []
      })
        .overrideTemplate(DoctorScheduleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoctorScheduleComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoctorScheduleService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DoctorSchedule(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.doctorSchedules[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
