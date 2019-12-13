import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IMedicalPrescription, MedicalPrescription } from 'app/shared/model/medical-prescription.model';
import { MedicalPrescriptionService } from './medical-prescription.service';
import { IDoctor } from 'app/shared/model/doctor.model';
import { DoctorService } from 'app/entities/doctor/doctor.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';
import { IPatientAppointement } from 'app/shared/model/patient-appointement.model';
import { PatientAppointementService } from 'app/entities/patient-appointement/patient-appointement.service';

@Component({
  selector: 'jhi-medical-prescription-update',
  templateUrl: './medical-prescription-update.component.html'
})
export class MedicalPrescriptionUpdateComponent implements OnInit {
  isSaving: boolean;

  doctors: IDoctor[];

  patients: IPatient[];

  patientappointements: IPatientAppointement[];

  editForm = this.fb.group({
    id: [],
    prescriptionDateTime: [],
    observations: [],
    doctor: [],
    patient: [],
    appointement: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected medicalPrescriptionService: MedicalPrescriptionService,
    protected doctorService: DoctorService,
    protected patientService: PatientService,
    protected patientAppointementService: PatientAppointementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ medicalPrescription }) => {
      this.updateForm(medicalPrescription);
    });
    this.doctorService
      .query()
      .subscribe((res: HttpResponse<IDoctor[]>) => (this.doctors = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.patientService
      .query()
      .subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.patientAppointementService
      .query()
      .subscribe(
        (res: HttpResponse<IPatientAppointement[]>) => (this.patientappointements = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(medicalPrescription: IMedicalPrescription) {
    this.editForm.patchValue({
      id: medicalPrescription.id,
      prescriptionDateTime:
        medicalPrescription.prescriptionDateTime != null ? medicalPrescription.prescriptionDateTime.format(DATE_TIME_FORMAT) : null,
      observations: medicalPrescription.observations,
      doctor: medicalPrescription.doctor,
      patient: medicalPrescription.patient,
      appointement: medicalPrescription.appointement
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const medicalPrescription = this.createFromForm();
    if (medicalPrescription.id !== undefined) {
      this.subscribeToSaveResponse(this.medicalPrescriptionService.update(medicalPrescription));
    } else {
      this.subscribeToSaveResponse(this.medicalPrescriptionService.create(medicalPrescription));
    }
  }

  private createFromForm(): IMedicalPrescription {
    return {
      ...new MedicalPrescription(),
      id: this.editForm.get(['id']).value,
      prescriptionDateTime:
        this.editForm.get(['prescriptionDateTime']).value != null
          ? moment(this.editForm.get(['prescriptionDateTime']).value, DATE_TIME_FORMAT)
          : undefined,
      observations: this.editForm.get(['observations']).value,
      doctor: this.editForm.get(['doctor']).value,
      patient: this.editForm.get(['patient']).value,
      appointement: this.editForm.get(['appointement']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicalPrescription>>) {
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

  trackDoctorById(index: number, item: IDoctor) {
    return item.id;
  }

  trackPatientById(index: number, item: IPatient) {
    return item.id;
  }

  trackPatientAppointementById(index: number, item: IPatientAppointement) {
    return item.id;
  }
}
