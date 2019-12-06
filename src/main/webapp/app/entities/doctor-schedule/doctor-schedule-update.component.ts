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
import { IDoctorSchedule, DoctorSchedule } from 'app/shared/model/doctor-schedule.model';
import { DoctorScheduleService } from './doctor-schedule.service';
import { IHealthCentreDoctor } from 'app/shared/model/health-centre-doctor.model';
import { HealthCentreDoctorService } from 'app/entities/health-centre-doctor/health-centre-doctor.service';

@Component({
  selector: 'jhi-doctor-schedule-update',
  templateUrl: './doctor-schedule-update.component.html'
})
export class DoctorScheduleUpdateComponent implements OnInit {
  isSaving: boolean;

  healthcentredoctors: IHealthCentreDoctor[];

  editForm = this.fb.group({
    id: [],
    scheduleStartDate: [],
    scheduleEndDate: [],
    healthCentreDoctor: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected doctorScheduleService: DoctorScheduleService,
    protected healthCentreDoctorService: HealthCentreDoctorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ doctorSchedule }) => {
      this.updateForm(doctorSchedule);
    });
    this.healthCentreDoctorService
      .query()
      .subscribe(
        (res: HttpResponse<IHealthCentreDoctor[]>) => (this.healthcentredoctors = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(doctorSchedule: IDoctorSchedule) {
    this.editForm.patchValue({
      id: doctorSchedule.id,
      scheduleStartDate: doctorSchedule.scheduleStartDate != null ? doctorSchedule.scheduleStartDate.format(DATE_TIME_FORMAT) : null,
      scheduleEndDate: doctorSchedule.scheduleEndDate != null ? doctorSchedule.scheduleEndDate.format(DATE_TIME_FORMAT) : null,
      healthCentreDoctor: doctorSchedule.healthCentreDoctor
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const doctorSchedule = this.createFromForm();
    if (doctorSchedule.id !== undefined) {
      this.subscribeToSaveResponse(this.doctorScheduleService.update(doctorSchedule));
    } else {
      this.subscribeToSaveResponse(this.doctorScheduleService.create(doctorSchedule));
    }
  }

  private createFromForm(): IDoctorSchedule {
    return {
      ...new DoctorSchedule(),
      id: this.editForm.get(['id']).value,
      scheduleStartDate:
        this.editForm.get(['scheduleStartDate']).value != null
          ? moment(this.editForm.get(['scheduleStartDate']).value, DATE_TIME_FORMAT)
          : undefined,
      scheduleEndDate:
        this.editForm.get(['scheduleEndDate']).value != null
          ? moment(this.editForm.get(['scheduleEndDate']).value, DATE_TIME_FORMAT)
          : undefined,
      healthCentreDoctor: this.editForm.get(['healthCentreDoctor']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoctorSchedule>>) {
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
}
