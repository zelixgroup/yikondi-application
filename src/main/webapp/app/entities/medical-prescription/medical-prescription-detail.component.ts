import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IMedicalPrescription } from 'app/shared/model/medical-prescription.model';

@Component({
  selector: 'jhi-medical-prescription-detail',
  templateUrl: './medical-prescription-detail.component.html'
})
export class MedicalPrescriptionDetailComponent implements OnInit {
  medicalPrescription: IMedicalPrescription;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ medicalPrescription }) => {
      this.medicalPrescription = medicalPrescription;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
