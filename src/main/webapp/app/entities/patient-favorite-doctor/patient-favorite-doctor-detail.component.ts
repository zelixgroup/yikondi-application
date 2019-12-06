import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPatientFavoriteDoctor } from 'app/shared/model/patient-favorite-doctor.model';

@Component({
  selector: 'jhi-patient-favorite-doctor-detail',
  templateUrl: './patient-favorite-doctor-detail.component.html'
})
export class PatientFavoriteDoctorDetailComponent implements OnInit {
  patientFavoriteDoctor: IPatientFavoriteDoctor;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ patientFavoriteDoctor }) => {
      this.patientFavoriteDoctor = patientFavoriteDoctor;
    });
  }

  previousState() {
    window.history.back();
  }
}
