import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDrugAdministrationRoute, DrugAdministrationRoute } from 'app/shared/model/drug-administration-route.model';
import { DrugAdministrationRouteService } from './drug-administration-route.service';

@Component({
  selector: 'jhi-drug-administration-route-update',
  templateUrl: './drug-administration-route-update.component.html'
})
export class DrugAdministrationRouteUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: []
  });

  constructor(
    protected drugAdministrationRouteService: DrugAdministrationRouteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ drugAdministrationRoute }) => {
      this.updateForm(drugAdministrationRoute);
    });
  }

  updateForm(drugAdministrationRoute: IDrugAdministrationRoute) {
    this.editForm.patchValue({
      id: drugAdministrationRoute.id,
      name: drugAdministrationRoute.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const drugAdministrationRoute = this.createFromForm();
    if (drugAdministrationRoute.id !== undefined) {
      this.subscribeToSaveResponse(this.drugAdministrationRouteService.update(drugAdministrationRoute));
    } else {
      this.subscribeToSaveResponse(this.drugAdministrationRouteService.create(drugAdministrationRoute));
    }
  }

  private createFromForm(): IDrugAdministrationRoute {
    return {
      ...new DrugAdministrationRoute(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDrugAdministrationRoute>>) {
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
