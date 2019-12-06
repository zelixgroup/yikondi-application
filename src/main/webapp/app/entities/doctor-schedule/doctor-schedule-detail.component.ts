import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDoctorSchedule } from 'app/shared/model/doctor-schedule.model';

@Component({
  selector: 'jhi-doctor-schedule-detail',
  templateUrl: './doctor-schedule-detail.component.html'
})
export class DoctorScheduleDetailComponent implements OnInit {
  doctorSchedule: IDoctorSchedule;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ doctorSchedule }) => {
      this.doctorSchedule = doctorSchedule;
    });
  }

  previousState() {
    window.history.back();
  }
}
