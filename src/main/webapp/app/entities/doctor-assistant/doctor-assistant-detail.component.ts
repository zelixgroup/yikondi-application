import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDoctorAssistant } from 'app/shared/model/doctor-assistant.model';

@Component({
  selector: 'jhi-doctor-assistant-detail',
  templateUrl: './doctor-assistant-detail.component.html'
})
export class DoctorAssistantDetailComponent implements OnInit {
  doctorAssistant: IDoctorAssistant;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ doctorAssistant }) => {
      this.doctorAssistant = doctorAssistant;
    });
  }

  previousState() {
    window.history.back();
  }
}
