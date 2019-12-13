import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMedicalPrescriptionAnalysis } from 'app/shared/model/medical-prescription-analysis.model';

@Component({
  selector: 'jhi-medical-prescription-analysis-detail',
  templateUrl: './medical-prescription-analysis-detail.component.html'
})
export class MedicalPrescriptionAnalysisDetailComponent implements OnInit {
  medicalPrescriptionAnalysis: IMedicalPrescriptionAnalysis;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ medicalPrescriptionAnalysis }) => {
      this.medicalPrescriptionAnalysis = medicalPrescriptionAnalysis;
    });
  }

  previousState() {
    window.history.back();
  }
}
