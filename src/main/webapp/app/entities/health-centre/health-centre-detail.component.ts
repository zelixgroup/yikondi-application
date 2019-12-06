import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHealthCentre } from 'app/shared/model/health-centre.model';

@Component({
  selector: 'jhi-health-centre-detail',
  templateUrl: './health-centre-detail.component.html'
})
export class HealthCentreDetailComponent implements OnInit {
  healthCentre: IHealthCentre;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ healthCentre }) => {
      this.healthCentre = healthCentre;
    });
  }

  previousState() {
    window.history.back();
  }
}
