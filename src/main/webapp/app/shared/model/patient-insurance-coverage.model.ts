import { Moment } from 'moment';
import { IAddress } from 'app/shared/model/address.model';
import { IPatient } from 'app/shared/model/patient.model';
import { IInsurance } from 'app/shared/model/insurance.model';

export interface IPatientInsuranceCoverage {
  id?: number;
  startDate?: Moment;
  endDate?: Moment;
  referenceNumber?: string;
  address?: IAddress;
  patient?: IPatient;
  insurance?: IInsurance;
}

export class PatientInsuranceCoverage implements IPatientInsuranceCoverage {
  constructor(
    public id?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public referenceNumber?: string,
    public address?: IAddress,
    public patient?: IPatient,
    public insurance?: IInsurance
  ) {}
}
