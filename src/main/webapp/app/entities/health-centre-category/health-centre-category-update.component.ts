import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IHealthCentreCategory, HealthCentreCategory } from 'app/shared/model/health-centre-category.model';
import { HealthCentreCategoryService } from './health-centre-category.service';

@Component({
  selector: 'jhi-health-centre-category-update',
  templateUrl: './health-centre-category-update.component.html'
})
export class HealthCentreCategoryUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: []
  });

  constructor(
    protected healthCentreCategoryService: HealthCentreCategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ healthCentreCategory }) => {
      this.updateForm(healthCentreCategory);
    });
  }

  updateForm(healthCentreCategory: IHealthCentreCategory) {
    this.editForm.patchValue({
      id: healthCentreCategory.id,
      name: healthCentreCategory.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const healthCentreCategory = this.createFromForm();
    if (healthCentreCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.healthCentreCategoryService.update(healthCentreCategory));
    } else {
      this.subscribeToSaveResponse(this.healthCentreCategoryService.create(healthCentreCategory));
    }
  }

  private createFromForm(): IHealthCentreCategory {
    return {
      ...new HealthCentreCategory(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHealthCentreCategory>>) {
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
