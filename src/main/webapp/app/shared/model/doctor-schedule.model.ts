import { Moment } from 'moment';
import { IHealthCentreDoctor } from 'app/shared/model/health-centre-doctor.model';

export interface IDoctorSchedule {
  id?: number;
  scheduleStartDate?: Moment;
  scheduleEndDate?: Moment;
  healthCentreDoctor?: IHealthCentreDoctor;
}

export class DoctorSchedule implements IDoctorSchedule {
  constructor(
    public id?: number,
    public scheduleStartDate?: Moment,
    public scheduleEndDate?: Moment,
    public healthCentreDoctor?: IHealthCentreDoctor
  ) {}
}
