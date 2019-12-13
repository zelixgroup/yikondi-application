import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IFamilyMember, FamilyMember } from 'app/shared/model/family-member.model';
import { FamilyMemberService } from './family-member.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';
import { IFamilyRelationship } from 'app/shared/model/family-relationship.model';
import { FamilyRelationshipService } from 'app/entities/family-relationship/family-relationship.service';

@Component({
  selector: 'jhi-family-member-update',
  templateUrl: './family-member-update.component.html'
})
export class FamilyMemberUpdateComponent implements OnInit {
  isSaving: boolean;

  patients: IPatient[];

  familyrelationships: IFamilyRelationship[];

  editForm = this.fb.group({
    id: [],
    observations: [],
    owner: [],
    member: [],
    relationship: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected familyMemberService: FamilyMemberService,
    protected patientService: PatientService,
    protected familyRelationshipService: FamilyRelationshipService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ familyMember }) => {
      this.updateForm(familyMember);
    });
    this.patientService
      .query()
      .subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.familyRelationshipService
      .query()
      .subscribe(
        (res: HttpResponse<IFamilyRelationship[]>) => (this.familyrelationships = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(familyMember: IFamilyMember) {
    this.editForm.patchValue({
      id: familyMember.id,
      observations: familyMember.observations,
      owner: familyMember.owner,
      member: familyMember.member,
      relationship: familyMember.relationship
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const familyMember = this.createFromForm();
    if (familyMember.id !== undefined) {
      this.subscribeToSaveResponse(this.familyMemberService.update(familyMember));
    } else {
      this.subscribeToSaveResponse(this.familyMemberService.create(familyMember));
    }
  }

  private createFromForm(): IFamilyMember {
    return {
      ...new FamilyMember(),
      id: this.editForm.get(['id']).value,
      observations: this.editForm.get(['observations']).value,
      owner: this.editForm.get(['owner']).value,
      member: this.editForm.get(['member']).value,
      relationship: this.editForm.get(['relationship']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamilyMember>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPatientById(index: number, item: IPatient) {
    return item.id;
  }

  trackFamilyRelationshipById(index: number, item: IFamilyRelationship) {
    return item.id;
  }
}
