import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPatientFavoriteDoctor } from 'app/shared/model/patient-favorite-doctor.model';
import { PatientFavoriteDoctorService } from './patient-favorite-doctor.service';
import { PatientFavoriteDoctorDeleteDialogComponent } from './patient-favorite-doctor-delete-dialog.component';

@Component({
  selector: 'jhi-patient-favorite-doctor',
  templateUrl: './patient-favorite-doctor.component.html'
})
export class PatientFavoriteDoctorComponent implements OnInit, OnDestroy {
  patientFavoriteDoctors: IPatientFavoriteDoctor[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected patientFavoriteDoctorService: PatientFavoriteDoctorService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.patientFavoriteDoctorService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IPatientFavoriteDoctor[]>) => (this.patientFavoriteDoctors = res.body));
      return;
    }
    this.patientFavoriteDoctorService.query().subscribe((res: HttpResponse<IPatientFavoriteDoctor[]>) => {
      this.patientFavoriteDoctors = res.body;
      this.currentSearch = '';
    });
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPatientFavoriteDoctors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPatientFavoriteDoctor) {
    return item.id;
  }

  registerChangeInPatientFavoriteDoctors() {
    this.eventSubscriber = this.eventManager.subscribe('patientFavoriteDoctorListModification', () => this.loadAll());
  }

  delete(patientFavoriteDoctor: IPatientFavoriteDoctor) {
    const modalRef = this.modalService.open(PatientFavoriteDoctorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.patientFavoriteDoctor = patientFavoriteDoctor;
  }
}
