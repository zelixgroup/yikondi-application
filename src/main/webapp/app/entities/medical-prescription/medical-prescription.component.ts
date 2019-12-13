import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMedicalPrescription } from 'app/shared/model/medical-prescription.model';
import { MedicalPrescriptionService } from './medical-prescription.service';
import { MedicalPrescriptionDeleteDialogComponent } from './medical-prescription-delete-dialog.component';

@Component({
  selector: 'jhi-medical-prescription',
  templateUrl: './medical-prescription.component.html'
})
export class MedicalPrescriptionComponent implements OnInit, OnDestroy {
  medicalPrescriptions: IMedicalPrescription[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected medicalPrescriptionService: MedicalPrescriptionService,
    protected dataUtils: JhiDataUtils,
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
      this.medicalPrescriptionService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IMedicalPrescription[]>) => (this.medicalPrescriptions = res.body));
      return;
    }
    this.medicalPrescriptionService.query().subscribe((res: HttpResponse<IMedicalPrescription[]>) => {
      this.medicalPrescriptions = res.body;
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
    this.registerChangeInMedicalPrescriptions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMedicalPrescription) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInMedicalPrescriptions() {
    this.eventSubscriber = this.eventManager.subscribe('medicalPrescriptionListModification', () => this.loadAll());
  }

  delete(medicalPrescription: IMedicalPrescription) {
    const modalRef = this.modalService.open(MedicalPrescriptionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.medicalPrescription = medicalPrescription;
  }
}
