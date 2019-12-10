import { Moment } from 'moment';
import { IPatient } from 'app/shared/model/patient.model';
import { IHealthCentreDoctor } from 'app/shared/model/health-centre-doctor.model';

export interface IPatientAppointement {
  id?: number;
  appointementDateTime?: Moment;
  appointementMakingDateTime?: Moment;
  booker?: IPatient;
  consultationPatient?: IPatient;
  healthCentreDoctor?: IHealthCentreDoctor;
}

export class PatientAppointement implements IPatientAppointement {
  constructor(
    public id?: number,
    public appointementDateTime?: Moment,
    public appointementMakingDateTime?: Moment,
    public booker?: IPatient,
    public consultationPatient?: IPatient,
    public healthCentreDoctor?: IHealthCentreDoctor
  ) {}
}
