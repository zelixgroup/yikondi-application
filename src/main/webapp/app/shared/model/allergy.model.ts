export interface IAllergy {
  id?: number;
  name?: string;
  description?: string;
}

export class Allergy implements IAllergy {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
