import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { DoctorScheduleDetailComponent } from 'app/entities/doctor-schedule/doctor-schedule-detail.component';
import { DoctorSchedule } from 'app/shared/model/doctor-schedule.model';

describe('Component Tests', () => {
  describe('DoctorSchedule Management Detail Component', () => {
    let comp: DoctorScheduleDetailComponent;
    let fixture: ComponentFixture<DoctorScheduleDetailComponent>;
    const route = ({ data: of({ doctorSchedule: new DoctorSchedule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DoctorScheduleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DoctorScheduleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoctorScheduleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.doctorSchedule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
