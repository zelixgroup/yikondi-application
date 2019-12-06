import { Moment } from 'moment';
import { IPatient } from 'app/shared/model/patient.model';
import { IPharmacy } from 'app/shared/model/pharmacy.model';

export interface IPatientFavoritePharmacy {
  id?: number;
  activationDate?: Moment;
  patient?: IPatient;
  pharmacy?: IPharmacy;
}

export class PatientFavoritePharmacy implements IPatientFavoritePharmacy {
  constructor(public id?: number, public activationDate?: Moment, public patient?: IPatient, public pharmacy?: IPharmacy) {}
}
