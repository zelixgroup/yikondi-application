export interface IDrugAdministrationRoute {
  id?: number;
  name?: string;
}

export class DrugAdministrationRoute implements IDrugAdministrationRoute {
  constructor(public id?: number, public name?: string) {}
}
