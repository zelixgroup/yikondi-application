import { ICountry } from 'app/shared/model/country.model';

export interface ICity {
  id?: number;
  name?: string;
  country?: ICountry;
}

export class City implements ICity {
  constructor(public id?: number, public name?: string, public country?: ICountry) {}
}
