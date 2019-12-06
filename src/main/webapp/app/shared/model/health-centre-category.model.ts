export interface IHealthCentreCategory {
  id?: number;
  name?: string;
}

export class HealthCentreCategory implements IHealthCentreCategory {
  constructor(public id?: number, public name?: string) {}
}
