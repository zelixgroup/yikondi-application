import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IDoctorWorkingSlot, DoctorWorkingSlot } from 'app/shared/model/doctor-working-slot.model';
import { DoctorWorkingSlotService } from './doctor-working-slot.service';
import { IDoctorSchedule } from 'app/shared/model/doctor-schedule.model';
import { DoctorScheduleService } from 'app/entities/doctor-schedule/doctor-schedule.service';

@Component({
  selector: 'jhi-doctor-working-slot-update',
  templateUrl: './doctor-working-slot-update.component.html'
})
export class DoctorWorkingSlotUpdateComponent implements OnInit {
  isSaving: boolean;

  doctorschedules: IDoctorSchedule[];

  editForm = this.fb.group({
    id: [],
    dayOfTheWeek: [],
    startTime: [],
    endTime: [],
    description: [],
    doctorSchedule: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected doctorWorkingSlotService: DoctorWorkingSlotService,
    protected doctorScheduleService: DoctorScheduleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ doctorWorkingSlot }) => {
      this.updateForm(doctorWorkingSlot);
    });
    this.doctorScheduleService
      .query()
      .subscribe(
        (res: HttpResponse<IDoctorSchedule[]>) => (this.doctorschedules = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(doctorWorkingSlot: IDoctorWorkingSlot) {
    this.editForm.patchValue({
      id: doctorWorkingSlot.id,
      dayOfTheWeek: doctorWorkingSlot.dayOfTheWeek,
      startTime: doctorWorkingSlot.startTime,
      endTime: doctorWorkingSlot.endTime,
      description: doctorWorkingSlot.description,
      doctorSchedule: doctorWorkingSlot.doctorSchedule
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const doctorWorkingSlot = this.createFromForm();
    if (doctorWorkingSlot.id !== undefined) {
      this.subscribeToSaveResponse(this.doctorWorkingSlotService.update(doctorWorkingSlot));
    } else {
      this.subscribeToSaveResponse(this.doctorWorkingSlotService.create(doctorWorkingSlot));
    }
  }

  private createFromForm(): IDoctorWorkingSlot {
    return {
      ...new DoctorWorkingSlot(),
      id: this.editForm.get(['id']).value,
      dayOfTheWeek: this.editForm.get(['dayOfTheWeek']).value,
      startTime: this.editForm.get(['startTime']).value,
      endTime: this.editForm.get(['endTime']).value,
      description: this.editForm.get(['description']).value,
      doctorSchedule: this.editForm.get(['doctorSchedule']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoctorWorkingSlot>>) {
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

  trackDoctorScheduleById(index: number, item: IDoctorSchedule) {
    return item.id;
  }
}
