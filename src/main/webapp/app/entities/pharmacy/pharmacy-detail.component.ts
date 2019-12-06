import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPharmacy } from 'app/shared/model/pharmacy.model';

@Component({
  selector: 'jhi-pharmacy-detail',
  templateUrl: './pharmacy-detail.component.html'
})
export class PharmacyDetailComponent implements OnInit {
  pharmacy: IPharmacy;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pharmacy }) => {
      this.pharmacy = pharmacy;
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
