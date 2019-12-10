import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPatientPathology } from 'app/shared/model/patient-pathology.model';

@Component({
  selector: 'jhi-patient-pathology-detail',
  templateUrl: './patient-pathology-detail.component.html'
})
export class PatientPathologyDetailComponent implements OnInit {
  patientPathology: IPatientPathology;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ patientPathology }) => {
      this.patientPathology = patientPathology;
    });
  }

  previousState() {
    window.history.back();
  }
}
