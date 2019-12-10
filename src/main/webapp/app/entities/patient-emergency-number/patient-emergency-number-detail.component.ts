import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPatientEmergencyNumber } from 'app/shared/model/patient-emergency-number.model';

@Component({
  selector: 'jhi-patient-emergency-number-detail',
  templateUrl: './patient-emergency-number-detail.component.html'
})
export class PatientEmergencyNumberDetailComponent implements OnInit {
  patientEmergencyNumber: IPatientEmergencyNumber;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ patientEmergencyNumber }) => {
      this.patientEmergencyNumber = patientEmergencyNumber;
    });
  }

  previousState() {
    window.history.back();
  }
}
