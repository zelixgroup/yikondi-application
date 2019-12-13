import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ILifeConstant, LifeConstant } from 'app/shared/model/life-constant.model';
import { LifeConstantService } from './life-constant.service';
import { ILifeConstantUnit } from 'app/shared/model/life-constant-unit.model';
import { LifeConstantUnitService } from 'app/entities/life-constant-unit/life-constant-unit.service';

@Component({
  selector: 'jhi-life-constant-update',
  templateUrl: './life-constant-update.component.html'
})
export class LifeConstantUpdateComponent implements OnInit {
  isSaving: boolean;

  lifeconstantunits: ILifeConstantUnit[];

  editForm = this.fb.group({
    id: [],
    name: [],
    lifeConstantUnit: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected lifeConstantService: LifeConstantService,
    protected lifeConstantUnitService: LifeConstantUnitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ lifeConstant }) => {
      this.updateForm(lifeConstant);
    });
    this.lifeConstantUnitService
      .query()
      .subscribe(
        (res: HttpResponse<ILifeConstantUnit[]>) => (this.lifeconstantunits = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(lifeConstant: ILifeConstant) {
    this.editForm.patchValue({
      id: lifeConstant.id,
      name: lifeConstant.name,
      lifeConstantUnit: lifeConstant.lifeConstantUnit
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const lifeConstant = this.createFromForm();
    if (lifeConstant.id !== undefined) {
      this.subscribeToSaveResponse(this.lifeConstantService.update(lifeConstant));
    } else {
      this.subscribeToSaveResponse(this.lifeConstantService.create(lifeConstant));
    }
  }

  private createFromForm(): ILifeConstant {
    return {
      ...new LifeConstant(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      lifeConstantUnit: this.editForm.get(['lifeConstantUnit']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILifeConstant>>) {
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

  trackLifeConstantUnitById(index: number, item: ILifeConstantUnit) {
    return item.id;
  }
}
