import { LifeConstantName } from 'app/shared/model/enumerations/life-constant-name.model';
import { LifeConstantUnit } from 'app/shared/model/enumerations/life-constant-unit.model';

export interface ILifeConstant {
  id?: number;
  lifeConstantName?: LifeConstantName;
  lifeConstantUnit?: LifeConstantUnit;
}

export class LifeConstant implements ILifeConstant {
  constructor(public id?: number, public lifeConstantName?: LifeConstantName, public lifeConstantUnit?: LifeConstantUnit) {}
}
