import { IDrugAdministrationRoute } from 'app/shared/model/drug-administration-route.model';
import { IDrugDosageForm } from 'app/shared/model/drug-dosage-form.model';

export interface IDrug {
  id?: number;
  name?: string;
  description?: string;
  administrationRoute?: IDrugAdministrationRoute;
  dosageForm?: IDrugDosageForm;
}

export class Drug implements IDrug {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public administrationRoute?: IDrugAdministrationRoute,
    public dosageForm?: IDrugDosageForm
  ) {}
}
