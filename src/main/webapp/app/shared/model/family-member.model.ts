import { IPatient } from 'app/shared/model/patient.model';
import { IFamilyRelationship } from 'app/shared/model/family-relationship.model';

export interface IFamilyMember {
  id?: number;
  observations?: string;
  owner?: IPatient;
  member?: IPatient;
  relationship?: IFamilyRelationship;
}

export class FamilyMember implements IFamilyMember {
  constructor(
    public id?: number,
    public observations?: string,
    public owner?: IPatient,
    public member?: IPatient,
    public relationship?: IFamilyRelationship
  ) {}
}
