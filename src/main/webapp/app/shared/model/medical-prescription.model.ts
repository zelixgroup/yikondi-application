import { Moment } from 'moment';
import { IDoctor } from 'app/shared/model/doctor.model';
import { IPatient } from 'app/shared/model/patient.model';
import { IPatientAppointement } from 'app/shared/model/patient-appointement.model';

export interface IMedicalPrescription {
  id?: number;
  prescriptionDateTime?: Moment;
  observations?: any;
  doctor?: IDoctor;
  patient?: IPatient;
  appointement?: IPatientAppointement;
}

export class MedicalPrescription implements IMedicalPrescription {
  constructor(
    public id?: number,
    public prescriptionDateTime?: Moment,
    public observations?: any,
    public doctor?: IDoctor,
    public patient?: IPatient,
    public appointement?: IPatientAppointement
  ) {}
}
