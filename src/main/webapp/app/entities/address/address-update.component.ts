import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IAddress, Address } from 'app/shared/model/address.model';
import { AddressService } from './address.service';
import { ICity } from 'app/shared/model/city.model';
import { CityService } from 'app/entities/city/city.service';
import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from 'app/entities/country/country.service';

@Component({
  selector: 'jhi-address-update',
  templateUrl: './address-update.component.html'
})
export class AddressUpdateComponent implements OnInit {
  isSaving: boolean;

  cities: ICity[];

  countries: ICountry[];

  editForm = this.fb.group({
    id: [],
    location: [],
    geolocation: [],
    primaryPhoneNumber: [],
    secondaryPhoneNumber: [],
    emailAddress: [],
    city: [],
    country: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected addressService: AddressService,
    protected cityService: CityService,
    protected countryService: CountryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ address }) => {
      this.updateForm(address);
    });
    this.cityService
      .query()
      .subscribe((res: HttpResponse<ICity[]>) => (this.cities = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.countryService
      .query()
      .subscribe((res: HttpResponse<ICountry[]>) => (this.countries = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(address: IAddress) {
    this.editForm.patchValue({
      id: address.id,
      location: address.location,
      geolocation: address.geolocation,
      primaryPhoneNumber: address.primaryPhoneNumber,
      secondaryPhoneNumber: address.secondaryPhoneNumber,
      emailAddress: address.emailAddress,
      city: address.city,
      country: address.country
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const address = this.createFromForm();
    if (address.id !== undefined) {
      this.subscribeToSaveResponse(this.addressService.update(address));
    } else {
      this.subscribeToSaveResponse(this.addressService.create(address));
    }
  }

  private createFromForm(): IAddress {
    return {
      ...new Address(),
      id: this.editForm.get(['id']).value,
      location: this.editForm.get(['location']).value,
      geolocation: this.editForm.get(['geolocation']).value,
      primaryPhoneNumber: this.editForm.get(['primaryPhoneNumber']).value,
      secondaryPhoneNumber: this.editForm.get(['secondaryPhoneNumber']).value,
      emailAddress: this.editForm.get(['emailAddress']).value,
      city: this.editForm.get(['city']).value,
      country: this.editForm.get(['country']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddress>>) {
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

  trackCityById(index: number, item: ICity) {
    return item.id;
  }

  trackCountryById(index: number, item: ICountry) {
    return item.id;
  }
}
