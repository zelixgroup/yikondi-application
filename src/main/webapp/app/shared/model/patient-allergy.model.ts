import { Moment } from 'moment';
import { IPatient } from 'app/shared/model/patient.model';
import { IAllergy } from 'app/shared/model/allergy.model';

export interface IPatientAllergy {
  id?: number;
  observationDate?: Moment;
  observations?: string;
  patient?: IPatient;
  allergy?: IAllergy;
}

export class PatientAllergy implements IPatientAllergy {
  constructor(
    public id?: number,
    public observationDate?: Moment,
    public observations?: string,
    public patient?: IPatient,
    public allergy?: IAllergy
  ) {}
}
