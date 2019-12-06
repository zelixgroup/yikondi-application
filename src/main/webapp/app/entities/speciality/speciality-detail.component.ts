import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpeciality } from 'app/shared/model/speciality.model';

@Component({
  selector: 'jhi-speciality-detail',
  templateUrl: './speciality-detail.component.html'
})
export class SpecialityDetailComponent implements OnInit {
  speciality: ISpeciality;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ speciality }) => {
      this.speciality = speciality;
    });
  }

  previousState() {
    window.history.back();
  }
}
