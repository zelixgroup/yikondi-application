export interface IAnalysis {
  id?: number;
  name?: string;
  description?: string;
}

export class Analysis implements IAnalysis {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
