import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMedicalPrescriptionAnalysis } from 'app/shared/model/medical-prescription-analysis.model';
import { MedicalPrescriptionAnalysisService } from './medical-prescription-analysis.service';
import { MedicalPrescriptionAnalysisDeleteDialogComponent } from './medical-prescription-analysis-delete-dialog.component';

@Component({
  selector: 'jhi-medical-prescription-analysis',
  templateUrl: './medical-prescription-analysis.component.html'
})
export class MedicalPrescriptionAnalysisComponent implements OnInit, OnDestroy {
  medicalPrescriptionAnalyses: IMedicalPrescriptionAnalysis[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected medicalPrescriptionAnalysisService: MedicalPrescriptionAnalysisService,
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
      this.medicalPrescriptionAnalysisService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IMedicalPrescriptionAnalysis[]>) => (this.medicalPrescriptionAnalyses = res.body));
      return;
    }
    this.medicalPrescriptionAnalysisService.query().subscribe((res: HttpResponse<IMedicalPrescriptionAnalysis[]>) => {
      this.medicalPrescriptionAnalyses = res.body;
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
    this.registerChangeInMedicalPrescriptionAnalyses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMedicalPrescriptionAnalysis) {
    return item.id;
  }

  registerChangeInMedicalPrescriptionAnalyses() {
    this.eventSubscriber = this.eventManager.subscribe('medicalPrescriptionAnalysisListModification', () => this.loadAll());
  }

  delete(medicalPrescriptionAnalysis: IMedicalPrescriptionAnalysis) {
    const modalRef = this.modalService.open(MedicalPrescriptionAnalysisDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.medicalPrescriptionAnalysis = medicalPrescriptionAnalysis;
  }
}
