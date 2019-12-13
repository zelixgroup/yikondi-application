export interface IDrugDosageForm {
  id?: number;
  name?: string;
}

export class DrugDosageForm implements IDrugDosageForm {
  constructor(public id?: number, public name?: string) {}
}
