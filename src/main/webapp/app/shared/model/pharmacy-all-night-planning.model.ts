import { Moment } from 'moment';
import { IPharmacy } from 'app/shared/model/pharmacy.model';

export interface IPharmacyAllNightPlanning {
  id?: number;
  plannedDate?: Moment;
  pharmacy?: IPharmacy;
}

export class PharmacyAllNightPlanning implements IPharmacyAllNightPlanning {
  constructor(public id?: number, public plannedDate?: Moment, public pharmacy?: IPharmacy) {}
}
