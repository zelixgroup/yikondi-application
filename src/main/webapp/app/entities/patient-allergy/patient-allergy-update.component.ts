import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPatientAllergy, PatientAllergy } from 'app/shared/model/patient-allergy.model';
import { PatientAllergyService } from './patient-allergy.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';
import { IAllergy } from 'app/shared/model/allergy.model';
import { AllergyService } from 'app/entities/allergy/allergy.service';

@Component({
  selector: 'jhi-patient-allergy-update',
  templateUrl: './patient-allergy-update.component.html'
})
export class PatientAllergyUpdateComponent implements OnInit {
  isSaving: boolean;

  patients: IPatient[];

  allergies: IAllergy[];
  observationDateDp: any;

  editForm = this.fb.group({
    id: [],
    observationDate: [],
    observations: [],
    patient: [],
    allergy: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected patientAllergyService: PatientAllergyService,
    protected patientService: PatientService,
    protected allergyService: AllergyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ patientAllergy }) => {
      this.updateForm(patientAllergy);
    });
    this.patientService
      .query()
      .subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.allergyService
      .query()
      .subscribe((res: HttpResponse<IAllergy[]>) => (this.allergies = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(patientAllergy: IPatientAllergy) {
    this.editForm.patchValue({
      id: patientAllergy.id,
      observationDate: patientAllergy.observationDate,
      observations: patientAllergy.observations,
      patient: patientAllergy.patient,
      allergy: patientAllergy.allergy
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const patientAllergy = this.createFromForm();
    if (patientAllergy.id !== undefined) {
      this.subscribeToSaveResponse(this.patientAllergyService.update(patientAllergy));
    } else {
      this.subscribeToSaveResponse(this.patientAllergyService.create(patientAllergy));
    }
  }

  private createFromForm(): IPatientAllergy {
    return {
      ...new PatientAllergy(),
      id: this.editForm.get(['id']).value,
      observationDate: this.editForm.get(['observationDate']).value,
      observations: this.editForm.get(['observations']).value,
      patient: this.editForm.get(['patient']).value,
      allergy: this.editForm.get(['allergy']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatientAllergy>>) {
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

  trackAllergyById(index: number, item: IAllergy) {
    return item.id;
  }
}
