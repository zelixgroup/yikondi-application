import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDoctorWorkingSlot } from 'app/shared/model/doctor-working-slot.model';

@Component({
  selector: 'jhi-doctor-working-slot-detail',
  templateUrl: './doctor-working-slot-detail.component.html'
})
export class DoctorWorkingSlotDetailComponent implements OnInit {
  doctorWorkingSlot: IDoctorWorkingSlot;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ doctorWorkingSlot }) => {
      this.doctorWorkingSlot = doctorWorkingSlot;
    });
  }

  previousState() {
    window.history.back();
  }
}
