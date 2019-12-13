import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInsuranceType } from 'app/shared/model/insurance-type.model';

@Component({
  selector: 'jhi-insurance-type-detail',
  templateUrl: './insurance-type-detail.component.html'
})
export class InsuranceTypeDetailComponent implements OnInit {
  insuranceType: IInsuranceType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ insuranceType }) => {
      this.insuranceType = insuranceType;
    });
  }

  previousState() {
    window.history.back();
  }
}
