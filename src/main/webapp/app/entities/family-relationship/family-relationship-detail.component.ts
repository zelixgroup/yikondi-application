import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFamilyRelationship } from 'app/shared/model/family-relationship.model';

@Component({
  selector: 'jhi-family-relationship-detail',
  templateUrl: './family-relationship-detail.component.html'
})
export class FamilyRelationshipDetailComponent implements OnInit {
  familyRelationship: IFamilyRelationship;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ familyRelationship }) => {
      this.familyRelationship = familyRelationship;
    });
  }

  previousState() {
    window.history.back();
  }
}
