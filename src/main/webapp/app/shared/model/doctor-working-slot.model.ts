import { IDoctorSchedule } from 'app/shared/model/doctor-schedule.model';
import { DayOfTheWeek } from 'app/shared/model/enumerations/day-of-the-week.model';

export interface IDoctorWorkingSlot {
  id?: number;
  dayOfTheWeek?: DayOfTheWeek;
  startTime?: string;
  endTime?: string;
  description?: string;
  doctorSchedule?: IDoctorSchedule;
}

export class DoctorWorkingSlot implements IDoctorWorkingSlot {
  constructor(
    public id?: number,
    public dayOfTheWeek?: DayOfTheWeek,
    public startTime?: string,
    public endTime?: string,
    public description?: string,
    public doctorSchedule?: IDoctorSchedule
  ) {}
}
