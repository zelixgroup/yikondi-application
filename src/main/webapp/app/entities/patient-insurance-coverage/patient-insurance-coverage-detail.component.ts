import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPatientInsuranceCoverage } from 'app/shared/model/patient-insurance-coverage.model';

@Component({
  selector: 'jhi-patient-insurance-coverage-detail',
  templateUrl: './patient-insurance-coverage-detail.component.html'
})
export class PatientInsuranceCoverageDetailComponent implements OnInit {
  patientInsuranceCoverage: IPatientInsuranceCoverage;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ patientInsuranceCoverage }) => {
      this.patientInsuranceCoverage = patientInsuranceCoverage;
    });
  }

  previousState() {
    window.history.back();
  }
}
