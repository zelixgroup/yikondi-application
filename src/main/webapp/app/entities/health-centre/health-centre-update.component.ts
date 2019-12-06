import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IHealthCentre, HealthCentre } from 'app/shared/model/health-centre.model';
import { HealthCentreService } from './health-centre.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';
import { IHealthCentreCategory } from 'app/shared/model/health-centre-category.model';
import { HealthCentreCategoryService } from 'app/entities/health-centre-category/health-centre-category.service';

@Component({
  selector: 'jhi-health-centre-update',
  templateUrl: './health-centre-update.component.html'
})
export class HealthCentreUpdateComponent implements OnInit {
  isSaving: boolean;

  addresses: IAddress[];

  healthcentrecategories: IHealthCentreCategory[];

  editForm = this.fb.group({
    id: [],
    name: [],
    logo: [],
    logoContentType: [],
    address: [],
    category: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected healthCentreService: HealthCentreService,
    protected addressService: AddressService,
    protected healthCentreCategoryService: HealthCentreCategoryService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ healthCentre }) => {
      this.updateForm(healthCentre);
    });
    this.addressService.query({ filter: 'healthcentre-is-null' }).subscribe(
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
    this.healthCentreCategoryService
      .query()
      .subscribe(
        (res: HttpResponse<IHealthCentreCategory[]>) => (this.healthcentrecategories = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(healthCentre: IHealthCentre) {
    this.editForm.patchValue({
      id: healthCentre.id,
      name: healthCentre.name,
      logo: healthCentre.logo,
      logoContentType: healthCentre.logoContentType,
      address: healthCentre.address,
      category: healthCentre.category
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

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const healthCentre = this.createFromForm();
    if (healthCentre.id !== undefined) {
      this.subscribeToSaveResponse(this.healthCentreService.update(healthCentre));
    } else {
      this.subscribeToSaveResponse(this.healthCentreService.create(healthCentre));
    }
  }

  private createFromForm(): IHealthCentre {
    return {
      ...new HealthCentre(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      logoContentType: this.editForm.get(['logoContentType']).value,
      logo: this.editForm.get(['logo']).value,
      address: this.editForm.get(['address']).value,
      category: this.editForm.get(['category']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHealthCentre>>) {
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

  trackHealthCentreCategoryById(index: number, item: IHealthCentreCategory) {
    return item.id;
  }
}
