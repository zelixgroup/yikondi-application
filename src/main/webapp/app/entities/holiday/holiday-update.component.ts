import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IHoliday, Holiday } from 'app/shared/model/holiday.model';
import { HolidayService } from './holiday.service';

@Component({
  selector: 'jhi-holiday-update',
  templateUrl: './holiday-update.component.html'
})
export class HolidayUpdateComponent implements OnInit {
  isSaving: boolean;
  correspondingDateDp: any;

  editForm = this.fb.group({
    id: [],
    label: [],
    correspondingDate: []
  });

  constructor(protected holidayService: HolidayService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ holiday }) => {
      this.updateForm(holiday);
    });
  }

  updateForm(holiday: IHoliday) {
    this.editForm.patchValue({
      id: holiday.id,
      label: holiday.label,
      correspondingDate: holiday.correspondingDate
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const holiday = this.createFromForm();
    if (holiday.id !== undefined) {
      this.subscribeToSaveResponse(this.holidayService.update(holiday));
    } else {
      this.subscribeToSaveResponse(this.holidayService.create(holiday));
    }
  }

  private createFromForm(): IHoliday {
    return {
      ...new Holiday(),
      id: this.editForm.get(['id']).value,
      label: this.editForm.get(['label']).value,
      correspondingDate: this.editForm.get(['correspondingDate']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHoliday>>) {
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
