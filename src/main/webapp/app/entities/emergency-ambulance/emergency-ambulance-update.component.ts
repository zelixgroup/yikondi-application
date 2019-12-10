import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IEmergencyAmbulance, EmergencyAmbulance } from 'app/shared/model/emergency-ambulance.model';
import { EmergencyAmbulanceService } from './emergency-ambulance.service';

@Component({
  selector: 'jhi-emergency-ambulance-update',
  templateUrl: './emergency-ambulance-update.component.html'
})
export class EmergencyAmbulanceUpdateComponent implements OnInit {
  isSaving: boolean;
  descriptionDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    phoneNumber: []
  });

  constructor(
    protected emergencyAmbulanceService: EmergencyAmbulanceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ emergencyAmbulance }) => {
      this.updateForm(emergencyAmbulance);
    });
  }

  updateForm(emergencyAmbulance: IEmergencyAmbulance) {
    this.editForm.patchValue({
      id: emergencyAmbulance.id,
      name: emergencyAmbulance.name,
      description: emergencyAmbulance.description,
      phoneNumber: emergencyAmbulance.phoneNumber
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const emergencyAmbulance = this.createFromForm();
    if (emergencyAmbulance.id !== undefined) {
      this.subscribeToSaveResponse(this.emergencyAmbulanceService.update(emergencyAmbulance));
    } else {
      this.subscribeToSaveResponse(this.emergencyAmbulanceService.create(emergencyAmbulance));
    }
  }

  private createFromForm(): IEmergencyAmbulance {
    return {
      ...new EmergencyAmbulance(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmergencyAmbulance>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
