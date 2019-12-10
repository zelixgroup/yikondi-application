import { Moment } from 'moment';
import { IPatient } from 'app/shared/model/patient.model';
import { IPathology } from 'app/shared/model/pathology.model';

export interface IPatientPathology {
  id?: number;
  observationDate?: Moment;
  observations?: string;
  patient?: IPatient;
  pathology?: IPathology;
}

export class PatientPathology implements IPatientPathology {
  constructor(
    public id?: number,
    public observationDate?: Moment,
    public observations?: string,
    public patient?: IPatient,
    public pathology?: IPathology
  ) {}
}
