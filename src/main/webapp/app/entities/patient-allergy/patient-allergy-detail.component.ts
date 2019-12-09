import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPatientAllergy } from 'app/shared/model/patient-allergy.model';

@Component({
  selector: 'jhi-patient-allergy-detail',
  templateUrl: './patient-allergy-detail.component.html'
})
export class PatientAllergyDetailComponent implements OnInit {
  patientAllergy: IPatientAllergy;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ patientAllergy }) => {
      this.patientAllergy = patientAllergy;
    });
  }

  previousState() {
    window.history.back();
  }
}
