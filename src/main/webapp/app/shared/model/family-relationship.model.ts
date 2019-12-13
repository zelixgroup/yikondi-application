export interface IFamilyRelationship {
  id?: number;
  name?: string;
  description?: string;
}

export class FamilyRelationship implements IFamilyRelationship {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
