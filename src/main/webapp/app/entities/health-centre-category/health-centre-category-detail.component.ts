import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHealthCentreCategory } from 'app/shared/model/health-centre-category.model';

@Component({
  selector: 'jhi-health-centre-category-detail',
  templateUrl: './health-centre-category-detail.component.html'
})
export class HealthCentreCategoryDetailComponent implements OnInit {
  healthCentreCategory: IHealthCentreCategory;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ healthCentreCategory }) => {
      this.healthCentreCategory = healthCentreCategory;
    });
  }

  previousState() {
    window.history.back();
  }
}
