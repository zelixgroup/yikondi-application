import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPatientInsuranceCoverage } from 'app/shared/model/patient-insurance-coverage.model';
import { PatientInsuranceCoverageService } from './patient-insurance-coverage.service';
import { PatientInsuranceCoverageDeleteDialogComponent } from './patient-insurance-coverage-delete-dialog.component';

@Component({
  selector: 'jhi-patient-insurance-coverage',
  templateUrl: './patient-insurance-coverage.component.html'
})
export class PatientInsuranceCoverageComponent implements OnInit, OnDestroy {
  patientInsuranceCoverages: IPatientInsuranceCoverage[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected patientInsuranceCoverageService: PatientInsuranceCoverageService,
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
      this.patientInsuranceCoverageService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IPatientInsuranceCoverage[]>) => (this.patientInsuranceCoverages = res.body));
      return;
    }
    this.patientInsuranceCoverageService.query().subscribe((res: HttpResponse<IPatientInsuranceCoverage[]>) => {
      this.patientInsuranceCoverages = res.body;
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
    this.registerChangeInPatientInsuranceCoverages();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPatientInsuranceCoverage) {
    return item.id;
  }

  registerChangeInPatientInsuranceCoverages() {
    this.eventSubscriber = this.eventManager.subscribe('patientInsuranceCoverageListModification', () => this.loadAll());
  }

  delete(patientInsuranceCoverage: IPatientInsuranceCoverage) {
    const modalRef = this.modalService.open(PatientInsuranceCoverageDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.patientInsuranceCoverage = patientInsuranceCoverage;
  }
}
