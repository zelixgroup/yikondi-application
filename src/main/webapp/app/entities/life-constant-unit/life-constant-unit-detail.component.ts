import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILifeConstantUnit } from 'app/shared/model/life-constant-unit.model';

@Component({
  selector: 'jhi-life-constant-unit-detail',
  templateUrl: './life-constant-unit-detail.component.html'
})
export class LifeConstantUnitDetailComponent implements OnInit {
  lifeConstantUnit: ILifeConstantUnit;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lifeConstantUnit }) => {
      this.lifeConstantUnit = lifeConstantUnit;
    });
  }

  previousState() {
    window.history.back();
  }
}
