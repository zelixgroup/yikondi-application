import { IAddress } from 'app/shared/model/address.model';

export interface IPharmacy {
  id?: number;
  name?: string;
  logoContentType?: string;
  logo?: any;
  address?: IAddress;
}

export class Pharmacy implements IPharmacy {
  constructor(public id?: number, public name?: string, public logoContentType?: string, public logo?: any, public address?: IAddress) {}
}
