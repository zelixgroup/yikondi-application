import { IHealthCentreDoctor } from 'app/shared/model/health-centre-doctor.model';
import { IPatient } from 'app/shared/model/patient.model';

export interface IDoctorAssistant {
  id?: number;
  canPrescribe?: boolean;
  healthCentreDoctor?: IHealthCentreDoctor;
  patient?: IPatient;
}

export class DoctorAssistant implements IDoctorAssistant {
  constructor(
    public id?: number,
    public canPrescribe?: boolean,
    public healthCentreDoctor?: IHealthCentreDoctor,
    public patient?: IPatient
  ) {
    this.canPrescribe = this.canPrescribe || false;
  }
}
