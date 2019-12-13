import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAnalysis, Analysis } from 'app/shared/model/analysis.model';
import { AnalysisService } from './analysis.service';

@Component({
  selector: 'jhi-analysis-update',
  templateUrl: './analysis-update.component.html'
})
export class AnalysisUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: []
  });

  constructor(protected analysisService: AnalysisService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ analysis }) => {
      this.updateForm(analysis);
    });
  }

  updateForm(analysis: IAnalysis) {
    this.editForm.patchValue({
      id: analysis.id,
      name: analysis.name,
      description: analysis.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const analysis = this.createFromForm();
    if (analysis.id !== undefined) {
      this.subscribeToSaveResponse(this.analysisService.update(analysis));
    } else {
      this.subscribeToSaveResponse(this.analysisService.create(analysis));
    }
  }

  private createFromForm(): IAnalysis {
    return {
      ...new Analysis(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnalysis>>) {
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
