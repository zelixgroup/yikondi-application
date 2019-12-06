import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IPatientFavoritePharmacy, PatientFavoritePharmacy } from 'app/shared/model/patient-favorite-pharmacy.model';
import { PatientFavoritePharmacyService } from './patient-favorite-pharmacy.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';
import { IPharmacy } from 'app/shared/model/pharmacy.model';
import { PharmacyService } from 'app/entities/pharmacy/pharmacy.service';

@Component({
  selector: 'jhi-patient-favorite-pharmacy-update',
  templateUrl: './patient-favorite-pharmacy-update.component.html'
})
export class PatientFavoritePharmacyUpdateComponent implements OnInit {
  isSaving: boolean;

  patients: IPatient[];

  pharmacies: IPharmacy[];

  editForm = this.fb.group({
    id: [],
    activationDate: [],
    patient: [],
    pharmacy: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected patientFavoritePharmacyService: PatientFavoritePharmacyService,
    protected patientService: PatientService,
    protected pharmacyService: PharmacyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ patientFavoritePharmacy }) => {
      this.updateForm(patientFavoritePharmacy);
    });
    this.patientService
      .query()
      .subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.pharmacyService
      .query()
      .subscribe((res: HttpResponse<IPharmacy[]>) => (this.pharmacies = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(patientFavoritePharmacy: IPatientFavoritePharmacy) {
    this.editForm.patchValue({
      id: patientFavoritePharmacy.id,
      activationDate:
        patientFavoritePharmacy.activationDate != null ? patientFavoritePharmacy.activationDate.format(DATE_TIME_FORMAT) : null,
      patient: patientFavoritePharmacy.patient,
      pharmacy: patientFavoritePharmacy.pharmacy
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const patientFavoritePharmacy = this.createFromForm();
    if (patientFavoritePharmacy.id !== undefined) {
      this.subscribeToSaveResponse(this.patientFavoritePharmacyService.update(patientFavoritePharmacy));
    } else {
      this.subscribeToSaveResponse(this.patientFavoritePharmacyService.create(patientFavoritePharmacy));
    }
  }

  private createFromForm(): IPatientFavoritePharmacy {
    return {
      ...new PatientFavoritePharmacy(),
      id: this.editForm.get(['id']).value,
      activationDate:
        this.editForm.get(['activationDate']).value != null
          ? moment(this.editForm.get(['activationDate']).value, DATE_TIME_FORMAT)
          : undefined,
      patient: this.editForm.get(['patient']).value,
      pharmacy: this.editForm.get(['pharmacy']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatientFavoritePharmacy>>) {
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

  trackPatientById(index: number, item: IPatient) {
    return item.id;
  }

  trackPharmacyById(index: number, item: IPharmacy) {
    return item.id;
  }
}
