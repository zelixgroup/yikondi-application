import { IAddress } from 'app/shared/model/address.model';
import { Civility } from 'app/shared/model/enumerations/civility.model';

export interface IPatient {
  id?: number;
  civility?: Civility;
  surname?: string;
  firstname?: string;
  address?: IAddress;
}

export class Patient implements IPatient {
  constructor(
    public id?: number,
    public civility?: Civility,
    public surname?: string,
    public firstname?: string,
    public address?: IAddress
  ) {}
}
