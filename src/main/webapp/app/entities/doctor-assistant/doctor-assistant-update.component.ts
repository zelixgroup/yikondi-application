import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IDoctorAssistant, DoctorAssistant } from 'app/shared/model/doctor-assistant.model';
import { DoctorAssistantService } from './doctor-assistant.service';
import { IHealthCentreDoctor } from 'app/shared/model/health-centre-doctor.model';
import { HealthCentreDoctorService } from 'app/entities/health-centre-doctor/health-centre-doctor.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';

@Component({
  selector: 'jhi-doctor-assistant-update',
  templateUrl: './doctor-assistant-update.component.html'
})
export class DoctorAssistantUpdateComponent implements OnInit {
  isSaving: boolean;

  healthcentredoctors: IHealthCentreDoctor[];

  patients: IPatient[];

  editForm = this.fb.group({
    id: [],
    canPrescribe: [],
    healthCentreDoctor: [],
    patient: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected doctorAssistantService: DoctorAssistantService,
    protected healthCentreDoctorService: HealthCentreDoctorService,
    protected patientService: PatientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ doctorAssistant }) => {
      this.updateForm(doctorAssistant);
    });
    this.healthCentreDoctorService
      .query()
      .subscribe(
        (res: HttpResponse<IHealthCentreDoctor[]>) => (this.healthcentredoctors = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.patientService
      .query()
      .subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(doctorAssistant: IDoctorAssistant) {
    this.editForm.patchValue({
      id: doctorAssistant.id,
      canPrescribe: doctorAssistant.canPrescribe,
      healthCentreDoctor: doctorAssistant.healthCentreDoctor,
      patient: doctorAssistant.patient
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const doctorAssistant = this.createFromForm();
    if (doctorAssistant.id !== undefined) {
      this.subscribeToSaveResponse(this.doctorAssistantService.update(doctorAssistant));
    } else {
      this.subscribeToSaveResponse(this.doctorAssistantService.create(doctorAssistant));
    }
  }

  private createFromForm(): IDoctorAssistant {
    return {
      ...new DoctorAssistant(),
      id: this.editForm.get(['id']).value,
      canPrescribe: this.editForm.get(['canPrescribe']).value,
      healthCentreDoctor: this.editForm.get(['healthCentreDoctor']).value,
      patient: this.editForm.get(['patient']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoctorAssistant>>) {
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

  trackHealthCentreDoctorById(index: number, item: IHealthCentreDoctor) {
    return item.id;
  }

  trackPatientById(index: number, item: IPatient) {
    return item.id;
  }
}
