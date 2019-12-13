export interface ILifeConstantUnit {
  id?: number;
  name?: string;
  description?: string;
}

export class LifeConstantUnit implements ILifeConstantUnit {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
