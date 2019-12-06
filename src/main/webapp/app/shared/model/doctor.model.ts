import { IPatient } from 'app/shared/model/patient.model';
import { ISpeciality } from 'app/shared/model/speciality.model';
import { Title } from 'app/shared/model/enumerations/title.model';

export interface IDoctor {
  id?: number;
  title?: Title;
  patient?: IPatient;
  speciality?: ISpeciality;
}

export class Doctor implements IDoctor {
  constructor(public id?: number, public title?: Title, public patient?: IPatient, public speciality?: ISpeciality) {}
}
