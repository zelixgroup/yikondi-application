import { IAddress } from 'app/shared/model/address.model';
import { IHealthCentreCategory } from 'app/shared/model/health-centre-category.model';

export interface IHealthCentre {
  id?: number;
  name?: string;
  address?: IAddress;
  category?: IHealthCentreCategory;
}

export class HealthCentre implements IHealthCentre {
  constructor(public id?: number, public name?: string, public address?: IAddress, public category?: IHealthCentreCategory) {}
}
