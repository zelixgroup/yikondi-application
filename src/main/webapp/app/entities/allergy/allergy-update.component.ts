import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAllergy, Allergy } from 'app/shared/model/allergy.model';
import { AllergyService } from './allergy.service';

@Component({
  selector: 'jhi-allergy-update',
  templateUrl: './allergy-update.component.html'
})
export class AllergyUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: []
  });

  constructor(protected allergyService: AllergyService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ allergy }) => {
      this.updateForm(allergy);
    });
  }

  updateForm(allergy: IAllergy) {
    this.editForm.patchValue({
      id: allergy.id,
      name: allergy.name,
      description: allergy.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const allergy = this.createFromForm();
    if (allergy.id !== undefined) {
      this.subscribeToSaveResponse(this.allergyService.update(allergy));
    } else {
      this.subscribeToSaveResponse(this.allergyService.create(allergy));
    }
  }

  private createFromForm(): IAllergy {
    return {
      ...new Allergy(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAllergy>>) {
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
