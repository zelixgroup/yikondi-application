import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPatientFavoritePharmacy } from 'app/shared/model/patient-favorite-pharmacy.model';

@Component({
  selector: 'jhi-patient-favorite-pharmacy-detail',
  templateUrl: './patient-favorite-pharmacy-detail.component.html'
})
export class PatientFavoritePharmacyDetailComponent implements OnInit {
  patientFavoritePharmacy: IPatientFavoritePharmacy;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ patientFavoritePharmacy }) => {
      this.patientFavoritePharmacy = patientFavoritePharmacy;
    });
  }

  previousState() {
    window.history.back();
  }
}
