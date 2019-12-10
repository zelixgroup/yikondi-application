import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IPatientEmergencyNumber, PatientEmergencyNumber } from 'app/shared/model/patient-emergency-number.model';
import { PatientEmergencyNumberService } from './patient-emergency-number.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';

@Component({
  selector: 'jhi-patient-emergency-number-update',
  templateUrl: './patient-emergency-number-update.component.html'
})
export class PatientEmergencyNumberUpdateComponent implements OnInit {
  isSaving: boolean;

  patients: IPatient[];

  editForm = this.fb.group({
    id: [],
    emergencyNumber: [],
    fullName: [],
    patient: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected patientEmergencyNumberService: PatientEmergencyNumberService,
    protected patientService: PatientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ patientEmergencyNumber }) => {
      this.updateForm(patientEmergencyNumber);
    });
    this.patientService
      .query()
      .subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(patientEmergencyNumber: IPatientEmergencyNumber) {
    this.editForm.patchValue({
      id: patientEmergencyNumber.id,
      emergencyNumber: patientEmergencyNumber.emergencyNumber,
      fullName: patientEmergencyNumber.fullName,
      patient: patientEmergencyNumber.patient
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const patientEmergencyNumber = this.createFromForm();
    if (patientEmergencyNumber.id !== undefined) {
      this.subscribeToSaveResponse(this.patientEmergencyNumberService.update(patientEmergencyNumber));
    } else {
      this.subscribeToSaveResponse(this.patientEmergencyNumberService.create(patientEmergencyNumber));
    }
  }

  private createFromForm(): IPatientEmergencyNumber {
    return {
      ...new PatientEmergencyNumber(),
      id: this.editForm.get(['id']).value,
      emergencyNumber: this.editForm.get(['emergencyNumber']).value,
      fullName: this.editForm.get(['fullName']).value,
      patient: this.editForm.get(['patient']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatientEmergencyNumber>>) {
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
