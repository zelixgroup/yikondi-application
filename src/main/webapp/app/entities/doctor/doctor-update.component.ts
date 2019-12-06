import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IDoctor, Doctor } from 'app/shared/model/doctor.model';
import { DoctorService } from './doctor.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';
import { ISpeciality } from 'app/shared/model/speciality.model';
import { SpecialityService } from 'app/entities/speciality/speciality.service';

@Component({
  selector: 'jhi-doctor-update',
  templateUrl: './doctor-update.component.html'
})
export class DoctorUpdateComponent implements OnInit {
  isSaving: boolean;

  patients: IPatient[];

  specialities: ISpeciality[];

  editForm = this.fb.group({
    id: [],
    title: [],
    patient: [],
    speciality: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected doctorService: DoctorService,
    protected patientService: PatientService,
    protected specialityService: SpecialityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ doctor }) => {
      this.updateForm(doctor);
    });
    this.patientService.query({ filter: 'doctor-is-null' }).subscribe(
      (res: HttpResponse<IPatient[]>) => {
        if (!this.editForm.get('patient').value || !this.editForm.get('patient').value.id) {
          this.patients = res.body;
        } else {
          this.patientService
            .find(this.editForm.get('patient').value.id)
            .subscribe(
              (subRes: HttpResponse<IPatient>) => (this.patients = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.specialityService
      .query()
      .subscribe(
        (res: HttpResponse<ISpeciality[]>) => (this.specialities = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(doctor: IDoctor) {
    this.editForm.patchValue({
      id: doctor.id,
      title: doctor.title,
      patient: doctor.patient,
      speciality: doctor.speciality
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const doctor = this.createFromForm();
    if (doctor.id !== undefined) {
      this.subscribeToSaveResponse(this.doctorService.update(doctor));
    } else {
      this.subscribeToSaveResponse(this.doctorService.create(doctor));
    }
  }

  private createFromForm(): IDoctor {
    return {
      ...new Doctor(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      patient: this.editForm.get(['patient']).value,
      speciality: this.editForm.get(['speciality']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoctor>>) {
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

  trackPatientById(index: number, item: IPatient) {
    return item.id;
  }

  trackSpecialityById(index: number, item: ISpeciality) {
    return item.id;
  }
}
