import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPatientAppointement } from 'app/shared/model/patient-appointement.model';

@Component({
  selector: 'jhi-patient-appointement-detail',
  templateUrl: './patient-appointement-detail.component.html'
})
export class PatientAppointementDetailComponent implements OnInit {
  patientAppointement: IPatientAppointement;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ patientAppointement }) => {
      this.patientAppointement = patientAppointement;
    });
  }

  previousState() {
    window.history.back();
  }
}
