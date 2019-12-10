import { Moment } from 'moment';
import { IAddress } from 'app/shared/model/address.model';
import { IUser } from 'app/core/user/user.model';
import { Civility } from 'app/shared/model/enumerations/civility.model';

export interface IPatient {
  id?: number;
  civility?: Civility;
  surname?: string;
  firstname?: string;
  pictureContentType?: string;
  picture?: any;
  dateOfBirth?: Moment;
  profession?: string;
  address?: IAddress;
  correspondingUser?: IUser;
}

export class Patient implements IPatient {
  constructor(
    public id?: number,
    public civility?: Civility,
    public surname?: string,
    public firstname?: string,
    public pictureContentType?: string,
    public picture?: any,
    public dateOfBirth?: Moment,
    public profession?: string,
    public address?: IAddress,
    public correspondingUser?: IUser
  ) {}
}
