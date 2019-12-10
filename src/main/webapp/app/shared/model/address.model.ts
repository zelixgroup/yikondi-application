import { ICity } from 'app/shared/model/city.model';

export interface IAddress {
  id?: number;
  location?: string;
  geolocation?: string;
  primaryPhoneNumber?: string;
  secondaryPhoneNumber?: string;
  emailAddress?: string;
  city?: ICity;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public location?: string,
    public geolocation?: string,
    public primaryPhoneNumber?: string,
    public secondaryPhoneNumber?: string,
    public emailAddress?: string,
    public city?: ICity
  ) {}
}
