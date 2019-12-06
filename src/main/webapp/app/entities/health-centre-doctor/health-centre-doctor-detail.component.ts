import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHealthCentreDoctor } from 'app/shared/model/health-centre-doctor.model';

@Component({
  selector: 'jhi-health-centre-doctor-detail',
  templateUrl: './health-centre-doctor-detail.component.html'
})
export class HealthCentreDoctorDetailComponent implements OnInit {
  healthCentreDoctor: IHealthCentreDoctor;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ healthCentreDoctor }) => {
      this.healthCentreDoctor = healthCentreDoctor;
    });
  }

  previousState() {
    window.history.back();
  }
}
