import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPharmacyAllNightPlanning } from 'app/shared/model/pharmacy-all-night-planning.model';

@Component({
  selector: 'jhi-pharmacy-all-night-planning-detail',
  templateUrl: './pharmacy-all-night-planning-detail.component.html'
})
export class PharmacyAllNightPlanningDetailComponent implements OnInit {
  pharmacyAllNightPlanning: IPharmacyAllNightPlanning;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pharmacyAllNightPlanning }) => {
      this.pharmacyAllNightPlanning = pharmacyAllNightPlanning;
    });
  }

  previousState() {
    window.history.back();
  }
}
