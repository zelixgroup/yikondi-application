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
import { IPatientFavoriteDoctor, PatientFavoriteDoctor } from 'app/shared/model/patient-favorite-doctor.model';
import { PatientFavoriteDoctorService } from './patient-favorite-doctor.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';
import { IDoctor } from 'app/shared/model/doctor.model';
import { DoctorService } from 'app/entities/doctor/doctor.service';

@Component({
  selector: 'jhi-patient-favorite-doctor-update',
  templateUrl: './patient-favorite-doctor-update.component.html'
})
export class PatientFavoriteDoctorUpdateComponent implements OnInit {
  isSaving: boolean;

  patients: IPatient[];

  doctors: IDoctor[];

  editForm = this.fb.group({
    id: [],
    activationDate: [],
    patient: [],
    doctor: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected patientFavoriteDoctorService: PatientFavoriteDoctorService,
    protected patientService: PatientService,
    protected doctorService: DoctorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ patientFavoriteDoctor }) => {
      this.updateForm(patientFavoriteDoctor);
    });
    this.patientService
      .query()
      .subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.doctorService
      .query()
      .subscribe((res: HttpResponse<IDoctor[]>) => (this.doctors = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(patientFavoriteDoctor: IPatientFavoriteDoctor) {
    this.editForm.patchValue({
      id: patientFavoriteDoctor.id,
      activationDate: patientFavoriteDoctor.activationDate != null ? patientFavoriteDoctor.activationDate.format(DATE_TIME_FORMAT) : null,
      patient: patientFavoriteDoctor.patient,
      doctor: patientFavoriteDoctor.doctor
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const patientFavoriteDoctor = this.createFromForm();
    if (patientFavoriteDoctor.id !== undefined) {
      this.subscribeToSaveResponse(this.patientFavoriteDoctorService.update(patientFavoriteDoctor));
    } else {
      this.subscribeToSaveResponse(this.patientFavoriteDoctorService.create(patientFavoriteDoctor));
    }
  }

  private createFromForm(): IPatientFavoriteDoctor {
    return {
      ...new PatientFavoriteDoctor(),
      id: this.editForm.get(['id']).value,
      activationDate:
        this.editForm.get(['activationDate']).value != null
          ? moment(this.editForm.get(['activationDate']).value, DATE_TIME_FORMAT)
          : undefined,
      patient: this.editForm.get(['patient']).value,
      doctor: this.editForm.get(['doctor']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatientFavoriteDoctor>>) {
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

  trackDoctorById(index: number, item: IDoctor) {
    return item.id;
  }
}
