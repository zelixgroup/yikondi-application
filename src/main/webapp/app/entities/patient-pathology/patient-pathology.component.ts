import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPatientPathology } from 'app/shared/model/patient-pathology.model';
import { PatientPathologyService } from './patient-pathology.service';
import { PatientPathologyDeleteDialogComponent } from './patient-pathology-delete-dialog.component';

@Component({
  selector: 'jhi-patient-pathology',
  templateUrl: './patient-pathology.component.html'
})
export class PatientPathologyComponent implements OnInit, OnDestroy {
  patientPathologies: IPatientPathology[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected patientPathologyService: PatientPathologyService,
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
      this.patientPathologyService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IPatientPathology[]>) => (this.patientPathologies = res.body));
      return;
    }
    this.patientPathologyService.query().subscribe((res: HttpResponse<IPatientPathology[]>) => {
      this.patientPathologies = res.body;
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
    this.registerChangeInPatientPathologies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPatientPathology) {
    return item.id;
  }

  registerChangeInPatientPathologies() {
    this.eventSubscriber = this.eventManager.subscribe('patientPathologyListModification', () => this.loadAll());
  }

  delete(patientPathology: IPatientPathology) {
    const modalRef = this.modalService.open(PatientPathologyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.patientPathology = patientPathology;
  }
}
