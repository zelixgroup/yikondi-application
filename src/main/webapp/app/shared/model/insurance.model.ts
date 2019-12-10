import { InsuranceType } from 'app/shared/model/enumerations/insurance-type.model';

export interface IInsurance {
  id?: number;
  name?: string;
  insuranceType?: InsuranceType;
  logoContentType?: string;
  logo?: any;
}

export class Insurance implements IInsurance {
  constructor(
    public id?: number,
    public name?: string,
    public insuranceType?: InsuranceType,
    public logoContentType?: string,
    public logo?: any
  ) {}
}
