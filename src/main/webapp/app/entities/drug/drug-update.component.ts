import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IDrug, Drug } from 'app/shared/model/drug.model';
import { DrugService } from './drug.service';
import { IDrugAdministrationRoute } from 'app/shared/model/drug-administration-route.model';
import { DrugAdministrationRouteService } from 'app/entities/drug-administration-route/drug-administration-route.service';
import { IDrugDosageForm } from 'app/shared/model/drug-dosage-form.model';
import { DrugDosageFormService } from 'app/entities/drug-dosage-form/drug-dosage-form.service';

@Component({
  selector: 'jhi-drug-update',
  templateUrl: './drug-update.component.html'
})
export class DrugUpdateComponent implements OnInit {
  isSaving: boolean;

  drugadministrationroutes: IDrugAdministrationRoute[];

  drugdosageforms: IDrugDosageForm[];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    administrationRoute: [],
    dosageForm: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected drugService: DrugService,
    protected drugAdministrationRouteService: DrugAdministrationRouteService,
    protected drugDosageFormService: DrugDosageFormService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ drug }) => {
      this.updateForm(drug);
    });
    this.drugAdministrationRouteService
      .query()
      .subscribe(
        (res: HttpResponse<IDrugAdministrationRoute[]>) => (this.drugadministrationroutes = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.drugDosageFormService
      .query()
      .subscribe(
        (res: HttpResponse<IDrugDosageForm[]>) => (this.drugdosageforms = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(drug: IDrug) {
    this.editForm.patchValue({
      id: drug.id,
      name: drug.name,
      description: drug.description,
      administrationRoute: drug.administrationRoute,
      dosageForm: drug.dosageForm
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const drug = this.createFromForm();
    if (drug.id !== undefined) {
      this.subscribeToSaveResponse(this.drugService.update(drug));
    } else {
      this.subscribeToSaveResponse(this.drugService.create(drug));
    }
  }

  private createFromForm(): IDrug {
    return {
      ...new Drug(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      administrationRoute: this.editForm.get(['administrationRoute']).value,
      dosageForm: this.editForm.get(['dosageForm']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDrug>>) {
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

  trackDrugAdministrationRouteById(index: number, item: IDrugAdministrationRoute) {
    return item.id;
  }

  trackDrugDosageFormById(index: number, item: IDrugDosageForm) {
    return item.id;
  }
}
