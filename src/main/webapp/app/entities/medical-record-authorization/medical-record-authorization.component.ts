import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMedicalRecordAuthorization } from 'app/shared/model/medical-record-authorization.model';
import { MedicalRecordAuthorizationService } from './medical-record-authorization.service';
import { MedicalRecordAuthorizationDeleteDialogComponent } from './medical-record-authorization-delete-dialog.component';

@Component({
  selector: 'jhi-medical-record-authorization',
  templateUrl: './medical-record-authorization.component.html'
})
export class MedicalRecordAuthorizationComponent implements OnInit, OnDestroy {
  medicalRecordAuthorizations: IMedicalRecordAuthorization[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected medicalRecordAuthorizationService: MedicalRecordAuthorizationService,
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
      this.medicalRecordAuthorizationService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IMedicalRecordAuthorization[]>) => (this.medicalRecordAuthorizations = res.body));
      return;
    }
    this.medicalRecordAuthorizationService.query().subscribe((res: HttpResponse<IMedicalRecordAuthorization[]>) => {
      this.medicalRecordAuthorizations = res.body;
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
    this.registerChangeInMedicalRecordAuthorizations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMedicalRecordAuthorization) {
    return item.id;
  }

  registerChangeInMedicalRecordAuthorizations() {
    this.eventSubscriber = this.eventManager.subscribe('medicalRecordAuthorizationListModification', () => this.loadAll());
  }

  delete(medicalRecordAuthorization: IMedicalRecordAuthorization) {
    const modalRef = this.modalService.open(MedicalRecordAuthorizationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.medicalRecordAuthorization = medicalRecordAuthorization;
  }
}
