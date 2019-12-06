import { Moment } from 'moment';
import { IHealthCentre } from 'app/shared/model/health-centre.model';
import { IDoctor } from 'app/shared/model/doctor.model';

export interface IHealthCentreDoctor {
  id?: number;
  startDate?: Moment;
  endDate?: Moment;
  consultingFees?: number;
  healthCentre?: IHealthCentre;
  doctor?: IDoctor;
}

export class HealthCentreDoctor implements IHealthCentreDoctor {
  constructor(
    public id?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public consultingFees?: number,
    public healthCentre?: IHealthCentre,
    public doctor?: IDoctor
  ) {}
}
