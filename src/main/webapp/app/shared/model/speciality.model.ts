export interface ISpeciality {
  id?: number;
  name?: string;
  description?: string;
}

export class Speciality implements ISpeciality {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
