import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMedicalPrescriptionDrug } from 'app/shared/model/medical-prescription-drug.model';

@Component({
  selector: 'jhi-medical-prescription-drug-detail',
  templateUrl: './medical-prescription-drug-detail.component.html'
})
export class MedicalPrescriptionDrugDetailComponent implements OnInit {
  medicalPrescriptionDrug: IMedicalPrescriptionDrug;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ medicalPrescriptionDrug }) => {
      this.medicalPrescriptionDrug = medicalPrescriptionDrug;
    });
  }

  previousState() {
    window.history.back();
  }
}
