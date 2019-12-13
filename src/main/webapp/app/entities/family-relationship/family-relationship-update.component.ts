import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IFamilyRelationship, FamilyRelationship } from 'app/shared/model/family-relationship.model';
import { FamilyRelationshipService } from './family-relationship.service';

@Component({
  selector: 'jhi-family-relationship-update',
  templateUrl: './family-relationship-update.component.html'
})
export class FamilyRelationshipUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: []
  });

  constructor(
    protected familyRelationshipService: FamilyRelationshipService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ familyRelationship }) => {
      this.updateForm(familyRelationship);
    });
  }

  updateForm(familyRelationship: IFamilyRelationship) {
    this.editForm.patchValue({
      id: familyRelationship.id,
      name: familyRelationship.name,
      description: familyRelationship.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const familyRelationship = this.createFromForm();
    if (familyRelationship.id !== undefined) {
      this.subscribeToSaveResponse(this.familyRelationshipService.update(familyRelationship));
    } else {
      this.subscribeToSaveResponse(this.familyRelationshipService.create(familyRelationship));
    }
  }

  private createFromForm(): IFamilyRelationship {
    return {
      ...new FamilyRelationship(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamilyRelationship>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
