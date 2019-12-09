import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPatientEmergencyNumber } from 'app/shared/model/patient-emergency-number.model';
import { PatientEmergencyNumberService } from './patient-emergency-number.service';
import { PatientEmergencyNumberDeleteDialogComponent } from './patient-emergency-number-delete-dialog.component';

@Component({
  selector: 'jhi-patient-emergency-number',
  templateUrl: './patient-emergency-number.component.html'
})
export class PatientEmergencyNumberComponent implements OnInit, OnDestroy {
  patientEmergencyNumbers: IPatientEmergencyNumber[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected patientEmergencyNumberService: PatientEmergencyNumberService,
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
      this.patientEmergencyNumberService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IPatientEmergencyNumber[]>) => (this.patientEmergencyNumbers = res.body));
      return;
    }
    this.patientEmergencyNumberService.query().subscribe((res: HttpResponse<IPatientEmergencyNumber[]>) => {
      this.patientEmergencyNumbers = res.body;
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
    this.registerChangeInPatientEmergencyNumbers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPatientEmergencyNumber) {
    return item.id;
  }

  registerChangeInPatientEmergencyNumbers() {
    this.eventSubscriber = this.eventManager.subscribe('patientEmergencyNumberListModification', () => this.loadAll());
  }

  delete(patientEmergencyNumber: IPatientEmergencyNumber) {
    const modalRef = this.modalService.open(PatientEmergencyNumberDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.patientEmergencyNumber = patientEmergencyNumber;
  }
}
