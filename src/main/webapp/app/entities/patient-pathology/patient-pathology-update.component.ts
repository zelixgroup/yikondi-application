import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPatientPathology, PatientPathology } from 'app/shared/model/patient-pathology.model';
import { PatientPathologyService } from './patient-pathology.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';
import { IPathology } from 'app/shared/model/pathology.model';
import { PathologyService } from 'app/entities/pathology/pathology.service';

@Component({
  selector: 'jhi-patient-pathology-update',
  templateUrl: './patient-pathology-update.component.html'
})
export class PatientPathologyUpdateComponent implements OnInit {
  isSaving: boolean;

  patients: IPatient[];

  pathologies: IPathology[];
  observationDateDp: any;

  editForm = this.fb.group({
    id: [],
    observationDate: [],
    observations: [],
    patient: [],
    pathology: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected patientPathologyService: PatientPathologyService,
    protected patientService: PatientService,
    protected pathologyService: PathologyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ patientPathology }) => {
      this.updateForm(patientPathology);
    });
    this.patientService
      .query()
      .subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.pathologyService
      .query()
      .subscribe((res: HttpResponse<IPathology[]>) => (this.pathologies = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(patientPathology: IPatientPathology) {
    this.editForm.patchValue({
      id: patientPathology.id,
      observationDate: patientPathology.observationDate,
      observations: patientPathology.observations,
      patient: patientPathology.patient,
      pathology: patientPathology.pathology
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const patientPathology = this.createFromForm();
    if (patientPathology.id !== undefined) {
      this.subscribeToSaveResponse(this.patientPathologyService.update(patientPathology));
    } else {
      this.subscribeToSaveResponse(this.patientPathologyService.create(patientPathology));
    }
  }

  private createFromForm(): IPatientPathology {
    return {
      ...new PatientPathology(),
      id: this.editForm.get(['id']).value,
      observationDate: this.editForm.get(['observationDate']).value,
      observations: this.editForm.get(['observations']).value,
      patient: this.editForm.get(['patient']).value,
      pathology: this.editForm.get(['pathology']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatientPathology>>) {
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

  trackPathologyById(index: number, item: IPathology) {
    return item.id;
  }
}
