import { Moment } from 'moment';
import { IPatient } from 'app/shared/model/patient.model';
import { ILifeConstant } from 'app/shared/model/life-constant.model';

export interface IPatientLifeConstant {
  id?: number;
  measurementDatetime?: Moment;
  measuredValue?: string;
  patient?: IPatient;
  lifeConstant?: ILifeConstant;
}

export class PatientLifeConstant implements IPatientLifeConstant {
  constructor(
    public id?: number,
    public measurementDatetime?: Moment,
    public measuredValue?: string,
    public patient?: IPatient,
    public lifeConstant?: ILifeConstant
  ) {}
}
