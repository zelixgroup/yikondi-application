import { IMedicalPrescription } from 'app/shared/model/medical-prescription.model';
import { IAnalysis } from 'app/shared/model/analysis.model';

export interface IMedicalPrescriptionAnalysis {
  id?: number;
  medicalPrescription?: IMedicalPrescription;
  analysis?: IAnalysis;
}

export class MedicalPrescriptionAnalysis implements IMedicalPrescriptionAnalysis {
  constructor(public id?: number, public medicalPrescription?: IMedicalPrescription, public analysis?: IAnalysis) {}
}
