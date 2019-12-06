import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IHealthCentre } from 'app/shared/model/health-centre.model';

@Component({
  selector: 'jhi-health-centre-detail',
  templateUrl: './health-centre-detail.component.html'
})
export class HealthCentreDetailComponent implements OnInit {
  healthCentre: IHealthCentre;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ healthCentre }) => {
      this.healthCentre = healthCentre;
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
