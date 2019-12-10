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
import { IPatientAppointement, PatientAppointement } from 'app/shared/model/patient-appointement.model';
import { PatientAppointementService } from './patient-appointement.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';
import { IHealthCentreDoctor } from 'app/shared/model/health-centre-doctor.model';
import { HealthCentreDoctorService } from 'app/entities/health-centre-doctor/health-centre-doctor.service';

@Component({
  selector: 'jhi-patient-appointement-update',
  templateUrl: './patient-appointement-update.component.html'
})
export class PatientAppointementUpdateComponent implements OnInit {
  isSaving: boolean;

  patients: IPatient[];

  healthcentredoctors: IHealthCentreDoctor[];

  editForm = this.fb.group({
    id: [],
    appointementDateTime: [],
    appointementMakingDateTime: [],
    booker: [],
    consultationPatient: [],
    healthCentreDoctor: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected patientAppointementService: PatientAppointementService,
    protected patientService: PatientService,
    protected healthCentreDoctorService: HealthCentreDoctorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ patientAppointement }) => {
      this.updateForm(patientAppointement);
    });
    this.patientService
      .query()
      .subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.healthCentreDoctorService
      .query()
      .subscribe(
        (res: HttpResponse<IHealthCentreDoctor[]>) => (this.healthcentredoctors = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(patientAppointement: IPatientAppointement) {
    this.editForm.patchValue({
      id: patientAppointement.id,
      appointementDateTime:
        patientAppointement.appointementDateTime != null ? patientAppointement.appointementDateTime.format(DATE_TIME_FORMAT) : null,
      appointementMakingDateTime:
        patientAppointement.appointementMakingDateTime != null
          ? patientAppointement.appointementMakingDateTime.format(DATE_TIME_FORMAT)
          : null,
      booker: patientAppointement.booker,
      consultationPatient: patientAppointement.consultationPatient,
      healthCentreDoctor: patientAppointement.healthCentreDoctor
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const patientAppointement = this.createFromForm();
    if (patientAppointement.id !== undefined) {
      this.subscribeToSaveResponse(this.patientAppointementService.update(patientAppointement));
    } else {
      this.subscribeToSaveResponse(this.patientAppointementService.create(patientAppointement));
    }
  }

  private createFromForm(): IPatientAppointement {
    return {
      ...new PatientAppointement(),
      id: this.editForm.get(['id']).value,
      appointementDateTime:
        this.editForm.get(['appointementDateTime']).value != null
          ? moment(this.editForm.get(['appointementDateTime']).value, DATE_TIME_FORMAT)
          : undefined,
      appointementMakingDateTime:
        this.editForm.get(['appointementMakingDateTime']).value != null
          ? moment(this.editForm.get(['appointementMakingDateTime']).value, DATE_TIME_FORMAT)
          : undefined,
      booker: this.editForm.get(['booker']).value,
      consultationPatient: this.editForm.get(['consultationPatient']).value,
      healthCentreDoctor: this.editForm.get(['healthCentreDoctor']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatientAppointement>>) {
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

  trackHealthCentreDoctorById(index: number, item: IHealthCentreDoctor) {
    return item.id;
  }
}
