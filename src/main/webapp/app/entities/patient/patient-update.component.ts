import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IPatient, Patient } from 'app/shared/model/patient.model';
import { PatientService } from './patient.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-patient-update',
  templateUrl: './patient-update.component.html'
})
export class PatientUpdateComponent implements OnInit {
  isSaving: boolean;

  addresses: IAddress[];

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    civility: [],
    surname: [],
    firstname: [],
    address: [],
    correspondingUser: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected patientService: PatientService,
    protected addressService: AddressService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ patient }) => {
      this.updateForm(patient);
    });
    this.addressService.query({ filter: 'patient-is-null' }).subscribe(
      (res: HttpResponse<IAddress[]>) => {
        if (!this.editForm.get('address').value || !this.editForm.get('address').value.id) {
          this.addresses = res.body;
        } else {
          this.addressService
            .find(this.editForm.get('address').value.id)
            .subscribe(
              (subRes: HttpResponse<IAddress>) => (this.addresses = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(patient: IPatient) {
    this.editForm.patchValue({
      id: patient.id,
      civility: patient.civility,
      surname: patient.surname,
      firstname: patient.firstname,
      address: patient.address,
      correspondingUser: patient.correspondingUser
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const patient = this.createFromForm();
    if (patient.id !== undefined) {
      this.subscribeToSaveResponse(this.patientService.update(patient));
    } else {
      this.subscribeToSaveResponse(this.patientService.create(patient));
    }
  }

  private createFromForm(): IPatient {
    return {
      ...new Patient(),
      id: this.editForm.get(['id']).value,
      civility: this.editForm.get(['civility']).value,
      surname: this.editForm.get(['surname']).value,
      firstname: this.editForm.get(['firstname']).value,
      address: this.editForm.get(['address']).value,
      correspondingUser: this.editForm.get(['correspondingUser']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatient>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAddressById(index: number, item: IAddress) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
