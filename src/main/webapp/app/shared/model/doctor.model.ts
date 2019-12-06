import { IAddress } from 'app/shared/model/address.model';
import { IPatient } from 'app/shared/model/patient.model';
import { ISpeciality } from 'app/shared/model/speciality.model';
import { Title } from 'app/shared/model/enumerations/title.model';

export interface IDoctor {
  id?: number;
  title?: Title;
  surname?: string;
  firstname?: string;
  address?: IAddress;
  patient?: IPatient;
  speciality?: ISpeciality;
}

export class Doctor implements IDoctor {
  constructor(
    public id?: number,
    public title?: Title,
    public surname?: string,
    public firstname?: string,
    public address?: IAddress,
    public patient?: IPatient,
    public speciality?: ISpeciality
  ) {}
}
