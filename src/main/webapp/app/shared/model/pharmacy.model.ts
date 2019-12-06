import { IAddress } from 'app/shared/model/address.model';

export interface IPharmacy {
  id?: number;
  name?: string;
  address?: IAddress;
}

export class Pharmacy implements IPharmacy {
  constructor(public id?: number, public name?: string, public address?: IAddress) {}
}
