import { IPatient } from 'app/shared/model/patient.model';

export interface IPatientEmergencyNumber {
  id?: number;
  emergencyNumber?: string;
  fullName?: string;
  patient?: IPatient;
}

export class PatientEmergencyNumber implements IPatientEmergencyNumber {
  constructor(public id?: number, public emergencyNumber?: string, public fullName?: string, public patient?: IPatient) {}
}
