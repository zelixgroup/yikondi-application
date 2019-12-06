import { Moment } from 'moment';
import { IPatient } from 'app/shared/model/patient.model';
import { IDoctor } from 'app/shared/model/doctor.model';

export interface IPatientFavoriteDoctor {
  id?: number;
  activationDate?: Moment;
  patient?: IPatient;
  doctor?: IDoctor;
}

export class PatientFavoriteDoctor implements IPatientFavoriteDoctor {
  constructor(public id?: number, public activationDate?: Moment, public patient?: IPatient, public doctor?: IDoctor) {}
}
