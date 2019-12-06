export interface ICountry {
  id?: number;
  isoCode?: string;
  name?: string;
  phoneReferenceNumber?: string;
}

export class Country implements ICountry {
  constructor(public id?: number, public isoCode?: string, public name?: string, public phoneReferenceNumber?: string) {}
}
