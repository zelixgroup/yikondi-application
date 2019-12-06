import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IPharmacy, Pharmacy } from 'app/shared/model/pharmacy.model';
import { PharmacyService } from './pharmacy.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';

@Component({
  selector: 'jhi-pharmacy-update',
  templateUrl: './pharmacy-update.component.html'
})
export class PharmacyUpdateComponent implements OnInit {
  isSaving: boolean;

  addresses: IAddress[];

  editForm = this.fb.group({
    id: [],
    name: [],
    address: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pharmacyService: PharmacyService,
    protected addressService: AddressService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pharmacy }) => {
      this.updateForm(pharmacy);
    });
    this.addressService.query({ filter: 'pharmacy-is-null' }).subscribe(
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
  }

  updateForm(pharmacy: IPharmacy) {
    this.editForm.patchValue({
      id: pharmacy.id,
      name: pharmacy.name,
      address: pharmacy.address
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pharmacy = this.createFromForm();
    if (pharmacy.id !== undefined) {
      this.subscribeToSaveResponse(this.pharmacyService.update(pharmacy));
    } else {
      this.subscribeToSaveResponse(this.pharmacyService.create(pharmacy));
    }
  }

  private createFromForm(): IPharmacy {
    return {
      ...new Pharmacy(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      address: this.editForm.get(['address']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPharmacy>>) {
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
}
