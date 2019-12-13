import { Moment } from 'moment';
import { IPatient } from 'app/shared/model/patient.model';

export interface IMedicalRecordAuthorization {
  id?: number;
  authorizationDateTime?: Moment;
  authorizationStartDateTime?: Moment;
  authorizationEndDateTime?: Moment;
  observations?: string;
  recordOwner?: IPatient;
  authorizationGrantee?: IPatient;
}

export class MedicalRecordAuthorization implements IMedicalRecordAuthorization {
  constructor(
    public id?: number,
    public authorizationDateTime?: Moment,
    public authorizationStartDateTime?: Moment,
    public authorizationEndDateTime?: Moment,
    public observations?: string,
    public recordOwner?: IPatient,
    public authorizationGrantee?: IPatient
  ) {}
}
