export interface IPathology {
  id?: number;
  name?: string;
  description?: string;
}

export class Pathology implements IPathology {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
