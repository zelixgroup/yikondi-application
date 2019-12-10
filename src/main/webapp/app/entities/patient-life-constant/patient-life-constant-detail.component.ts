import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPatientLifeConstant } from 'app/shared/model/patient-life-constant.model';

@Component({
  selector: 'jhi-patient-life-constant-detail',
  templateUrl: './patient-life-constant-detail.component.html'
})
export class PatientLifeConstantDetailComponent implements OnInit {
  patientLifeConstant: IPatientLifeConstant;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ patientLifeConstant }) => {
      this.patientLifeConstant = patientLifeConstant;
    });
  }

  previousState() {
    window.history.back();
  }
}
