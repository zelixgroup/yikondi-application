export interface IInsuranceType {
  id?: number;
  name?: string;
  description?: string;
}

export class InsuranceType implements IInsuranceType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
