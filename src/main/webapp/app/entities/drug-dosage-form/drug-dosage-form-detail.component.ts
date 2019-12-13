import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDrugDosageForm } from 'app/shared/model/drug-dosage-form.model';

@Component({
  selector: 'jhi-drug-dosage-form-detail',
  templateUrl: './drug-dosage-form-detail.component.html'
})
export class DrugDosageFormDetailComponent implements OnInit {
  drugDosageForm: IDrugDosageForm;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ drugDosageForm }) => {
      this.drugDosageForm = drugDosageForm;
    });
  }

  previousState() {
    window.history.back();
  }
}
