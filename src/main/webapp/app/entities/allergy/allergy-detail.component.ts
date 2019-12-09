import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAllergy } from 'app/shared/model/allergy.model';

@Component({
  selector: 'jhi-allergy-detail',
  templateUrl: './allergy-detail.component.html'
})
export class AllergyDetailComponent implements OnInit {
  allergy: IAllergy;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allergy }) => {
      this.allergy = allergy;
    });
  }

  previousState() {
    window.history.back();
  }
}
