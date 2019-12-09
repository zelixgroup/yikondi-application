import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IInsurance } from 'app/shared/model/insurance.model';

@Component({
  selector: 'jhi-insurance-detail',
  templateUrl: './insurance-detail.component.html'
})
export class InsuranceDetailComponent implements OnInit {
  insurance: IInsurance;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ insurance }) => {
      this.insurance = insurance;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
