import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILifeConstant } from 'app/shared/model/life-constant.model';

@Component({
  selector: 'jhi-life-constant-detail',
  templateUrl: './life-constant-detail.component.html'
})
export class LifeConstantDetailComponent implements OnInit {
  lifeConstant: ILifeConstant;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lifeConstant }) => {
      this.lifeConstant = lifeConstant;
    });
  }

  previousState() {
    window.history.back();
  }
}
