import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IMedicalPrescriptionAnalysis, MedicalPrescriptionAnalysis } from 'app/shared/model/medical-prescription-analysis.model';
import { MedicalPrescriptionAnalysisService } from './medical-prescription-analysis.service';
import { IMedicalPrescription } from 'app/shared/model/medical-prescription.model';
import { MedicalPrescriptionService } from 'app/entities/medical-prescription/medical-prescription.service';
import { IAnalysis } from 'app/shared/model/analysis.model';
import { AnalysisService } from 'app/entities/analysis/analysis.service';

@Component({
  selector: 'jhi-medical-prescription-analysis-update',
  templateUrl: './medical-prescription-analysis-update.component.html'
})
export class MedicalPrescriptionAnalysisUpdateComponent implements OnInit {
  isSaving: boolean;

  medicalprescriptions: IMedicalPrescription[];

  analyses: IAnalysis[];

  editForm = this.fb.group({
    id: [],
    medicalPrescription: [],
    analysis: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected medicalPrescriptionAnalysisService: MedicalPrescriptionAnalysisService,
    protected medicalPrescriptionService: MedicalPrescriptionService,
    protected analysisService: AnalysisService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ medicalPrescriptionAnalysis }) => {
      this.updateForm(medicalPrescriptionAnalysis);
    });
    this.medicalPrescriptionService
      .query()
      .subscribe(
        (res: HttpResponse<IMedicalPrescription[]>) => (this.medicalprescriptions = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.analysisService
      .query()
      .subscribe((res: HttpResponse<IAnalysis[]>) => (this.analyses = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(medicalPrescriptionAnalysis: IMedicalPrescriptionAnalysis) {
    this.editForm.patchValue({
      id: medicalPrescriptionAnalysis.id,
      medicalPrescription: medicalPrescriptionAnalysis.medicalPrescription,
      analysis: medicalPrescriptionAnalysis.analysis
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const medicalPrescriptionAnalysis = this.createFromForm();
    if (medicalPrescriptionAnalysis.id !== undefined) {
      this.subscribeToSaveResponse(this.medicalPrescriptionAnalysisService.update(medicalPrescriptionAnalysis));
    } else {
      this.subscribeToSaveResponse(this.medicalPrescriptionAnalysisService.create(medicalPrescriptionAnalysis));
    }
  }

  private createFromForm(): IMedicalPrescriptionAnalysis {
    return {
      ...new MedicalPrescriptionAnalysis(),
      id: this.editForm.get(['id']).value,
      medicalPrescription: this.editForm.get(['medicalPrescription']).value,
      analysis: this.editForm.get(['analysis']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicalPrescriptionAnalysis>>) {
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

  trackAnalysisById(index: number, item: IAnalysis) {
    return item.id;
  }
}
