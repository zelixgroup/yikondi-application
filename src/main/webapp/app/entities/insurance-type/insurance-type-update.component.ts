import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IInsuranceType, InsuranceType } from 'app/shared/model/insurance-type.model';
import { InsuranceTypeService } from './insurance-type.service';

@Component({
  selector: 'jhi-insurance-type-update',
  templateUrl: './insurance-type-update.component.html'
})
export class InsuranceTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: []
  });

  constructor(protected insuranceTypeService: InsuranceTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ insuranceType }) => {
      this.updateForm(insuranceType);
    });
  }

  updateForm(insuranceType: IInsuranceType) {
    this.editForm.patchValue({
      id: insuranceType.id,
      name: insuranceType.name,
      description: insuranceType.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const insuranceType = this.createFromForm();
    if (insuranceType.id !== undefined) {
      this.subscribeToSaveResponse(this.insuranceTypeService.update(insuranceType));
    } else {
      this.subscribeToSaveResponse(this.insuranceTypeService.create(insuranceType));
    }
  }

  private createFromForm(): IInsuranceType {
    return {
      ...new InsuranceType(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInsuranceType>>) {
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
