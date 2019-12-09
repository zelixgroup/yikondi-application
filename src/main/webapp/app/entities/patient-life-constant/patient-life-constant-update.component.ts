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
import { IPatientLifeConstant, PatientLifeConstant } from 'app/shared/model/patient-life-constant.model';
import { PatientLifeConstantService } from './patient-life-constant.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';
import { ILifeConstant } from 'app/shared/model/life-constant.model';
import { LifeConstantService } from 'app/entities/life-constant/life-constant.service';

@Component({
  selector: 'jhi-patient-life-constant-update',
  templateUrl: './patient-life-constant-update.component.html'
})
export class PatientLifeConstantUpdateComponent implements OnInit {
  isSaving: boolean;

  patients: IPatient[];

  lifeconstants: ILifeConstant[];

  editForm = this.fb.group({
    id: [],
    measurementDatetime: [],
    measuredValue: [],
    patient: [],
    lifeConstant: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected patientLifeConstantService: PatientLifeConstantService,
    protected patientService: PatientService,
    protected lifeConstantService: LifeConstantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ patientLifeConstant }) => {
      this.updateForm(patientLifeConstant);
    });
    this.patientService
      .query()
      .subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.lifeConstantService
      .query()
      .subscribe(
        (res: HttpResponse<ILifeConstant[]>) => (this.lifeconstants = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(patientLifeConstant: IPatientLifeConstant) {
    this.editForm.patchValue({
      id: patientLifeConstant.id,
      measurementDatetime:
        patientLifeConstant.measurementDatetime != null ? patientLifeConstant.measurementDatetime.format(DATE_TIME_FORMAT) : null,
      measuredValue: patientLifeConstant.measuredValue,
      patient: patientLifeConstant.patient,
      lifeConstant: patientLifeConstant.lifeConstant
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const patientLifeConstant = this.createFromForm();
    if (patientLifeConstant.id !== undefined) {
      this.subscribeToSaveResponse(this.patientLifeConstantService.update(patientLifeConstant));
    } else {
      this.subscribeToSaveResponse(this.patientLifeConstantService.create(patientLifeConstant));
    }
  }

  private createFromForm(): IPatientLifeConstant {
    return {
      ...new PatientLifeConstant(),
      id: this.editForm.get(['id']).value,
      measurementDatetime:
        this.editForm.get(['measurementDatetime']).value != null
          ? moment(this.editForm.get(['measurementDatetime']).value, DATE_TIME_FORMAT)
          : undefined,
      measuredValue: this.editForm.get(['measuredValue']).value,
      patient: this.editForm.get(['patient']).value,
      lifeConstant: this.editForm.get(['lifeConstant']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatientLifeConstant>>) {
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

  trackLifeConstantById(index: number, item: ILifeConstant) {
    return item.id;
  }
}
