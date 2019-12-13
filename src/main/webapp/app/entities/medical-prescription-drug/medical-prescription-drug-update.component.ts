import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IMedicalPrescriptionDrug, MedicalPrescriptionDrug } from 'app/shared/model/medical-prescription-drug.model';
import { MedicalPrescriptionDrugService } from './medical-prescription-drug.service';
import { IMedicalPrescription } from 'app/shared/model/medical-prescription.model';
import { MedicalPrescriptionService } from 'app/entities/medical-prescription/medical-prescription.service';
import { IDrug } from 'app/shared/model/drug.model';
import { DrugService } from 'app/entities/drug/drug.service';

@Component({
  selector: 'jhi-medical-prescription-drug-update',
  templateUrl: './medical-prescription-drug-update.component.html'
})
export class MedicalPrescriptionDrugUpdateComponent implements OnInit {
  isSaving: boolean;

  medicalprescriptions: IMedicalPrescription[];

  drugs: IDrug[];

  editForm = this.fb.group({
    id: [],
    dosage: [],
    medicalPrescription: [],
    drug: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected medicalPrescriptionDrugService: MedicalPrescriptionDrugService,
    protected medicalPrescriptionService: MedicalPrescriptionService,
    protected drugService: DrugService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ medicalPrescriptionDrug }) => {
      this.updateForm(medicalPrescriptionDrug);
    });
    this.medicalPrescriptionService
      .query()
      .subscribe(
        (res: HttpResponse<IMedicalPrescription[]>) => (this.medicalprescriptions = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.drugService
      .query()
      .subscribe((res: HttpResponse<IDrug[]>) => (this.drugs = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(medicalPrescriptionDrug: IMedicalPrescriptionDrug) {
    this.editForm.patchValue({
      id: medicalPrescriptionDrug.id,
      dosage: medicalPrescriptionDrug.dosage,
      medicalPrescription: medicalPrescriptionDrug.medicalPrescription,
      drug: medicalPrescriptionDrug.drug
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const medicalPrescriptionDrug = this.createFromForm();
    if (medicalPrescriptionDrug.id !== undefined) {
      this.subscribeToSaveResponse(this.medicalPrescriptionDrugService.update(medicalPrescriptionDrug));
    } else {
      this.subscribeToSaveResponse(this.medicalPrescriptionDrugService.create(medicalPrescriptionDrug));
    }
  }

  private createFromForm(): IMedicalPrescriptionDrug {
    return {
      ...new MedicalPrescriptionDrug(),
      id: this.editForm.get(['id']).value,
      dosage: this.editForm.get(['dosage']).value,
      medicalPrescription: this.editForm.get(['medicalPrescription']).value,
      drug: this.editForm.get(['drug']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicalPrescriptionDrug>>) {
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

  trackMedicalPrescriptionById(index: number, item: IMedicalPrescription) {
    return item.id;
  }

  trackDrugById(index: number, item: IDrug) {
    return item.id;
  }
}
