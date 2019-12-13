import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDrugDosageForm, DrugDosageForm } from 'app/shared/model/drug-dosage-form.model';
import { DrugDosageFormService } from './drug-dosage-form.service';

@Component({
  selector: 'jhi-drug-dosage-form-update',
  templateUrl: './drug-dosage-form-update.component.html'
})
export class DrugDosageFormUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: []
  });

  constructor(protected drugDosageFormService: DrugDosageFormService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ drugDosageForm }) => {
      this.updateForm(drugDosageForm);
    });
  }

  updateForm(drugDosageForm: IDrugDosageForm) {
    this.editForm.patchValue({
      id: drugDosageForm.id,
      name: drugDosageForm.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const drugDosageForm = this.createFromForm();
    if (drugDosageForm.id !== undefined) {
      this.subscribeToSaveResponse(this.drugDosageFormService.update(drugDosageForm));
    } else {
      this.subscribeToSaveResponse(this.drugDosageFormService.create(drugDosageForm));
    }
  }

  private createFromForm(): IDrugDosageForm {
    return {
      ...new DrugDosageForm(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDrugDosageForm>>) {
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
