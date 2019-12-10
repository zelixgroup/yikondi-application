import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPathology, Pathology } from 'app/shared/model/pathology.model';
import { PathologyService } from './pathology.service';

@Component({
  selector: 'jhi-pathology-update',
  templateUrl: './pathology-update.component.html'
})
export class PathologyUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: []
  });

  constructor(protected pathologyService: PathologyService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pathology }) => {
      this.updateForm(pathology);
    });
  }

  updateForm(pathology: IPathology) {
    this.editForm.patchValue({
      id: pathology.id,
      name: pathology.name,
      description: pathology.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pathology = this.createFromForm();
    if (pathology.id !== undefined) {
      this.subscribeToSaveResponse(this.pathologyService.update(pathology));
    } else {
      this.subscribeToSaveResponse(this.pathologyService.create(pathology));
    }
  }

  private createFromForm(): IPathology {
    return {
      ...new Pathology(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPathology>>) {
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
