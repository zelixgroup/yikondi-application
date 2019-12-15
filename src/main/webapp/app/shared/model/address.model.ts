import { ICity } from 'app/shared/model/city.model';
import { ICountry } from 'app/shared/model/country.model';

export interface IAddress {
  id?: number;
  location?: string;
  geolocation?: string;
  primaryPhoneNumber?: string;
  secondaryPhoneNumber?: string;
  emailAddress?: string;
  city?: ICity;
  country?: ICountry;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public location?: string,
    public geolocation?: string,
    public primaryPhoneNumber?: string,
    public secondaryPhoneNumber?: string,
    public emailAddress?: string,
    public city?: ICity,
    public country?: ICountry
  ) {}
}
