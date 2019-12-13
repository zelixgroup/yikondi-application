import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDrugAdministrationRoute } from 'app/shared/model/drug-administration-route.model';

@Component({
  selector: 'jhi-drug-administration-route-detail',
  templateUrl: './drug-administration-route-detail.component.html'
})
export class DrugAdministrationRouteDetailComponent implements OnInit {
  drugAdministrationRoute: IDrugAdministrationRoute;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ drugAdministrationRoute }) => {
      this.drugAdministrationRoute = drugAdministrationRoute;
    });
  }

  previousState() {
    window.history.back();
  }
}
