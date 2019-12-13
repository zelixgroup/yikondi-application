import { IMedicalPrescription } from 'app/shared/model/medical-prescription.model';
import { IDrug } from 'app/shared/model/drug.model';

export interface IMedicalPrescriptionDrug {
  id?: number;
  dosage?: string;
  medicalPrescription?: IMedicalPrescription;
  drug?: IDrug;
}

export class MedicalPrescriptionDrug implements IMedicalPrescriptionDrug {
  constructor(public id?: number, public dosage?: string, public medicalPrescription?: IMedicalPrescription, public drug?: IDrug) {}
}
