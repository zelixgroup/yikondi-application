import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IHealthCentreDoctor, HealthCentreDoctor } from 'app/shared/model/health-centre-doctor.model';
import { HealthCentreDoctorService } from './health-centre-doctor.service';
import { IHealthCentre } from 'app/shared/model/health-centre.model';
import { HealthCentreService } from 'app/entities/health-centre/health-centre.service';
import { IDoctor } from 'app/shared/model/doctor.model';
import { DoctorService } from 'app/entities/doctor/doctor.service';

@Component({
  selector: 'jhi-health-centre-doctor-update',
  templateUrl: './health-centre-doctor-update.component.html'
})
export class HealthCentreDoctorUpdateComponent implements OnInit {
  isSaving: boolean;

  healthcentres: IHealthCentre[];

  doctors: IDoctor[];
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    startDate: [],
    endDate: [],
    consultingFees: [],
    healthCentre: [],
    doctor: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected healthCentreDoctorService: HealthCentreDoctorService,
    protected healthCentreService: HealthCentreService,
    protected doctorService: DoctorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ healthCentreDoctor }) => {
      this.updateForm(healthCentreDoctor);
    });
    this.healthCentreService
      .query()
      .subscribe(
        (res: HttpResponse<IHealthCentre[]>) => (this.healthcentres = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.doctorService
      .query()
      .subscribe((res: HttpResponse<IDoctor[]>) => (this.doctors = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(healthCentreDoctor: IHealthCentreDoctor) {
    this.editForm.patchValue({
      id: healthCentreDoctor.id,
      startDate: healthCentreDoctor.startDate,
      endDate: healthCentreDoctor.endDate,
      consultingFees: healthCentreDoctor.consultingFees,
      healthCentre: healthCentreDoctor.healthCentre,
      doctor: healthCentreDoctor.doctor
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const healthCentreDoctor = this.createFromForm();
    if (healthCentreDoctor.id !== undefined) {
      this.subscribeToSaveResponse(this.healthCentreDoctorService.update(healthCentreDoctor));
    } else {
      this.subscribeToSaveResponse(this.healthCentreDoctorService.create(healthCentreDoctor));
    }
  }

  private createFromForm(): IHealthCentreDoctor {
    return {
      ...new HealthCentreDoctor(),
      id: this.editForm.get(['id']).value,
      startDate: this.editForm.get(['startDate']).value,
      endDate: this.editForm.get(['endDate']).value,
      consultingFees: this.editForm.get(['consultingFees']).value,
      healthCentre: this.editForm.get(['healthCentre']).value,
      doctor: this.editForm.get(['doctor']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHealthCentreDoctor>>) {
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

  trackHealthCentreById(index: number, item: IHealthCentre) {
    return item.id;
  }

  trackDoctorById(index: number, item: IDoctor) {
    return item.id;
  }
}
