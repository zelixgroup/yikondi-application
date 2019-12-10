import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPharmacyAllNightPlanning, PharmacyAllNightPlanning } from 'app/shared/model/pharmacy-all-night-planning.model';
import { PharmacyAllNightPlanningService } from './pharmacy-all-night-planning.service';
import { IPharmacy } from 'app/shared/model/pharmacy.model';
import { PharmacyService } from 'app/entities/pharmacy/pharmacy.service';

@Component({
  selector: 'jhi-pharmacy-all-night-planning-update',
  templateUrl: './pharmacy-all-night-planning-update.component.html'
})
export class PharmacyAllNightPlanningUpdateComponent implements OnInit {
  isSaving: boolean;

  pharmacies: IPharmacy[];
  plannedStartDateDp: any;
  plannedEndDateDp: any;

  editForm = this.fb.group({
    id: [],
    plannedStartDate: [],
    plannedEndDate: [],
    pharmacy: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pharmacyAllNightPlanningService: PharmacyAllNightPlanningService,
    protected pharmacyService: PharmacyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pharmacyAllNightPlanning }) => {
      this.updateForm(pharmacyAllNightPlanning);
    });
    this.pharmacyService
      .query()
      .subscribe((res: HttpResponse<IPharmacy[]>) => (this.pharmacies = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pharmacyAllNightPlanning: IPharmacyAllNightPlanning) {
    this.editForm.patchValue({
      id: pharmacyAllNightPlanning.id,
      plannedStartDate: pharmacyAllNightPlanning.plannedStartDate,
      plannedEndDate: pharmacyAllNightPlanning.plannedEndDate,
      pharmacy: pharmacyAllNightPlanning.pharmacy
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pharmacyAllNightPlanning = this.createFromForm();
    if (pharmacyAllNightPlanning.id !== undefined) {
      this.subscribeToSaveResponse(this.pharmacyAllNightPlanningService.update(pharmacyAllNightPlanning));
    } else {
      this.subscribeToSaveResponse(this.pharmacyAllNightPlanningService.create(pharmacyAllNightPlanning));
    }
  }

  private createFromForm(): IPharmacyAllNightPlanning {
    return {
      ...new PharmacyAllNightPlanning(),
      id: this.editForm.get(['id']).value,
      plannedStartDate: this.editForm.get(['plannedStartDate']).value,
      plannedEndDate: this.editForm.get(['plannedEndDate']).value,
      pharmacy: this.editForm.get(['pharmacy']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPharmacyAllNightPlanning>>) {
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

  trackPharmacyById(index: number, item: IPharmacy) {
    return item.id;
  }
}
