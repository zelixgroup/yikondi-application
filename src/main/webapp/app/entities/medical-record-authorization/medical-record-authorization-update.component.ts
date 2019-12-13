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
import { IMedicalRecordAuthorization, MedicalRecordAuthorization } from 'app/shared/model/medical-record-authorization.model';
import { MedicalRecordAuthorizationService } from './medical-record-authorization.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';

@Component({
  selector: 'jhi-medical-record-authorization-update',
  templateUrl: './medical-record-authorization-update.component.html'
})
export class MedicalRecordAuthorizationUpdateComponent implements OnInit {
  isSaving: boolean;

  patients: IPatient[];

  editForm = this.fb.group({
    id: [],
    authorizationDateTime: [],
    authorizationStartDateTime: [],
    authorizationEndDateTime: [],
    observations: [],
    recordOwner: [],
    authorizationGrantee: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected medicalRecordAuthorizationService: MedicalRecordAuthorizationService,
    protected patientService: PatientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ medicalRecordAuthorization }) => {
      this.updateForm(medicalRecordAuthorization);
    });
    this.patientService
      .query()
      .subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(medicalRecordAuthorization: IMedicalRecordAuthorization) {
    this.editForm.patchValue({
      id: medicalRecordAuthorization.id,
      authorizationDateTime:
        medicalRecordAuthorization.authorizationDateTime != null
          ? medicalRecordAuthorization.authorizationDateTime.format(DATE_TIME_FORMAT)
          : null,
      authorizationStartDateTime:
        medicalRecordAuthorization.authorizationStartDateTime != null
          ? medicalRecordAuthorization.authorizationStartDateTime.format(DATE_TIME_FORMAT)
          : null,
      authorizationEndDateTime:
        medicalRecordAuthorization.authorizationEndDateTime != null
          ? medicalRecordAuthorization.authorizationEndDateTime.format(DATE_TIME_FORMAT)
          : null,
      observations: medicalRecordAuthorization.observations,
      recordOwner: medicalRecordAuthorization.recordOwner,
      authorizationGrantee: medicalRecordAuthorization.authorizationGrantee
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const medicalRecordAuthorization = this.createFromForm();
    if (medicalRecordAuthorization.id !== undefined) {
      this.subscribeToSaveResponse(this.medicalRecordAuthorizationService.update(medicalRecordAuthorization));
    } else {
      this.subscribeToSaveResponse(this.medicalRecordAuthorizationService.create(medicalRecordAuthorization));
    }
  }

  private createFromForm(): IMedicalRecordAuthorization {
    return {
      ...new MedicalRecordAuthorization(),
      id: this.editForm.get(['id']).value,
      authorizationDateTime:
        this.editForm.get(['authorizationDateTime']).value != null
          ? moment(this.editForm.get(['authorizationDateTime']).value, DATE_TIME_FORMAT)
          : undefined,
      authorizationStartDateTime:
        this.editForm.get(['authorizationStartDateTime']).value != null
          ? moment(this.editForm.get(['authorizationStartDateTime']).value, DATE_TIME_FORMAT)
          : undefined,
      authorizationEndDateTime:
        this.editForm.get(['authorizationEndDateTime']).value != null
          ? moment(this.editForm.get(['authorizationEndDateTime']).value, DATE_TIME_FORMAT)
          : undefined,
      observations: this.editForm.get(['observations']).value,
      recordOwner: this.editForm.get(['recordOwner']).value,
      authorizationGrantee: this.editForm.get(['authorizationGrantee']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicalRecordAuthorization>>) {
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
}
