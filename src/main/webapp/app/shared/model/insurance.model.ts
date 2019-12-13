import { IInsuranceType } from 'app/shared/model/insurance-type.model';

export interface IInsurance {
  id?: number;
  name?: string;
  logoContentType?: string;
  logo?: any;
  insuranceType?: IInsuranceType;
}

export class Insurance implements IInsurance {
  constructor(
    public id?: number,
    public name?: string,
    public logoContentType?: string,
    public logo?: any,
    public insuranceType?: IInsuranceType
  ) {}
}
