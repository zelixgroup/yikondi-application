import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ILifeConstantUnit, LifeConstantUnit } from 'app/shared/model/life-constant-unit.model';
import { LifeConstantUnitService } from './life-constant-unit.service';

@Component({
  selector: 'jhi-life-constant-unit-update',
  templateUrl: './life-constant-unit-update.component.html'
})
export class LifeConstantUnitUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: []
  });

  constructor(
    protected lifeConstantUnitService: LifeConstantUnitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ lifeConstantUnit }) => {
      this.updateForm(lifeConstantUnit);
    });
  }

  updateForm(lifeConstantUnit: ILifeConstantUnit) {
    this.editForm.patchValue({
      id: lifeConstantUnit.id,
      name: lifeConstantUnit.name,
      description: lifeConstantUnit.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const lifeConstantUnit = this.createFromForm();
    if (lifeConstantUnit.id !== undefined) {
      this.subscribeToSaveResponse(this.lifeConstantUnitService.update(lifeConstantUnit));
    } else {
      this.subscribeToSaveResponse(this.lifeConstantUnitService.create(lifeConstantUnit));
    }
  }

  private createFromForm(): ILifeConstantUnit {
    return {
      ...new LifeConstantUnit(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILifeConstantUnit>>) {
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
