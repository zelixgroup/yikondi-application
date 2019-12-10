import { Moment } from 'moment';
import { IPharmacy } from 'app/shared/model/pharmacy.model';

export interface IPharmacyAllNightPlanning {
  id?: number;
  plannedStartDate?: Moment;
  plannedEndDate?: Moment;
  pharmacy?: IPharmacy;
}

export class PharmacyAllNightPlanning implements IPharmacyAllNightPlanning {
  constructor(public id?: number, public plannedStartDate?: Moment, public plannedEndDate?: Moment, public pharmacy?: IPharmacy) {}
}
