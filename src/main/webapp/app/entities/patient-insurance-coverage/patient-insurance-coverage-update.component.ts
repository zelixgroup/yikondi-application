import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPatientInsuranceCoverage, PatientInsuranceCoverage } from 'app/shared/model/patient-insurance-coverage.model';
import { PatientInsuranceCoverageService } from './patient-insurance-coverage.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';
import { IInsurance } from 'app/shared/model/insurance.model';
import { InsuranceService } from 'app/entities/insurance/insurance.service';

@Component({
  selector: 'jhi-patient-insurance-coverage-update',
  templateUrl: './patient-insurance-coverage-update.component.html'
})
export class PatientInsuranceCoverageUpdateComponent implements OnInit {
  isSaving: boolean;

  addresses: IAddress[];

  patients: IPatient[];

  insurances: IInsurance[];
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    startDate: [],
    endDate: [],
    referenceNumber: [],
    address: [],
    patient: [],
    insurance: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected patientInsuranceCoverageService: PatientInsuranceCoverageService,
    protected addressService: AddressService,
    protected patientService: PatientService,
    protected insuranceService: InsuranceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ patientInsuranceCoverage }) => {
      this.updateForm(patientInsuranceCoverage);
    });
    this.addressService.query({ filter: 'patientinsurancecoverage-is-null' }).subscribe(
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
    this.patientService
      .query()
      .subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.insuranceService
      .query()
      .subscribe((res: HttpResponse<IInsurance[]>) => (this.insurances = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(patientInsuranceCoverage: IPatientInsuranceCoverage) {
    this.editForm.patchValue({
      id: patientInsuranceCoverage.id,
      startDate: patientInsuranceCoverage.startDate,
      endDate: patientInsuranceCoverage.endDate,
      referenceNumber: patientInsuranceCoverage.referenceNumber,
      address: patientInsuranceCoverage.address,
      patient: patientInsuranceCoverage.patient,
      insurance: patientInsuranceCoverage.insurance
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const patientInsuranceCoverage = this.createFromForm();
    if (patientInsuranceCoverage.id !== undefined) {
      this.subscribeToSaveResponse(this.patientInsuranceCoverageService.update(patientInsuranceCoverage));
    } else {
      this.subscribeToSaveResponse(this.patientInsuranceCoverageService.create(patientInsuranceCoverage));
    }
  }

  private createFromForm(): IPatientInsuranceCoverage {
    return {
      ...new PatientInsuranceCoverage(),
      id: this.editForm.get(['id']).value,
      startDate: this.editForm.get(['startDate']).value,
      endDate: this.editForm.get(['endDate']).value,
      referenceNumber: this.editForm.get(['referenceNumber']).value,
      address: this.editForm.get(['address']).value,
      patient: this.editForm.get(['patient']).value,
      insurance: this.editForm.get(['insurance']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatientInsuranceCoverage>>) {
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

  trackPatientById(index: number, item: IPatient) {
    return item.id;
  }

  trackInsuranceById(index: number, item: IInsurance) {
    return item.id;
  }
}
