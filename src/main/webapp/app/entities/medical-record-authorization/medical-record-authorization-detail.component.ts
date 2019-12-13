import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMedicalRecordAuthorization } from 'app/shared/model/medical-record-authorization.model';

@Component({
  selector: 'jhi-medical-record-authorization-detail',
  templateUrl: './medical-record-authorization-detail.component.html'
})
export class MedicalRecordAuthorizationDetailComponent implements OnInit {
  medicalRecordAuthorization: IMedicalRecordAuthorization;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ medicalRecordAuthorization }) => {
      this.medicalRecordAuthorization = medicalRecordAuthorization;
    });
  }

  previousState() {
    window.history.back();
  }
}
