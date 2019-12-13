import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDrug } from 'app/shared/model/drug.model';

@Component({
  selector: 'jhi-drug-detail',
  templateUrl: './drug-detail.component.html'
})
export class DrugDetailComponent implements OnInit {
  drug: IDrug;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ drug }) => {
      this.drug = drug;
    });
  }

  previousState() {
    window.history.back();
  }
}
