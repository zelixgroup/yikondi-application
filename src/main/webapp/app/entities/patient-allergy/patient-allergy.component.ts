import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPatientAllergy } from 'app/shared/model/patient-allergy.model';
import { PatientAllergyService } from './patient-allergy.service';
import { PatientAllergyDeleteDialogComponent } from './patient-allergy-delete-dialog.component';

@Component({
  selector: 'jhi-patient-allergy',
  templateUrl: './patient-allergy.component.html'
})
export class PatientAllergyComponent implements OnInit, OnDestroy {
  patientAllergies: IPatientAllergy[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected patientAllergyService: PatientAllergyService,
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
      this.patientAllergyService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IPatientAllergy[]>) => (this.patientAllergies = res.body));
      return;
    }
    this.patientAllergyService.query().subscribe((res: HttpResponse<IPatientAllergy[]>) => {
      this.patientAllergies = res.body;
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
    this.registerChangeInPatientAllergies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPatientAllergy) {
    return item.id;
  }

  registerChangeInPatientAllergies() {
    this.eventSubscriber = this.eventManager.subscribe('patientAllergyListModification', () => this.loadAll());
  }

  delete(patientAllergy: IPatientAllergy) {
    const modalRef = this.modalService.open(PatientAllergyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.patientAllergy = patientAllergy;
  }
}
