import { ILifeConstantUnit } from 'app/shared/model/life-constant-unit.model';

export interface ILifeConstant {
  id?: number;
  name?: string;
  lifeConstantUnit?: ILifeConstantUnit;
}

export class LifeConstant implements ILifeConstant {
  constructor(public id?: number, public name?: string, public lifeConstantUnit?: ILifeConstantUnit) {}
}
