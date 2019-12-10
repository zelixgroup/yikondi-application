import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmergencyAmbulance } from 'app/shared/model/emergency-ambulance.model';

@Component({
  selector: 'jhi-emergency-ambulance-detail',
  templateUrl: './emergency-ambulance-detail.component.html'
})
export class EmergencyAmbulanceDetailComponent implements OnInit {
  emergencyAmbulance: IEmergencyAmbulance;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ emergencyAmbulance }) => {
      this.emergencyAmbulance = emergencyAmbulance;
    });
  }

  previousState() {
    window.history.back();
  }
}
