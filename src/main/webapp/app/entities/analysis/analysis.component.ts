import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnalysis } from 'app/shared/model/analysis.model';
import { AnalysisService } from './analysis.service';
import { AnalysisDeleteDialogComponent } from './analysis-delete-dialog.component';

@Component({
  selector: 'jhi-analysis',
  templateUrl: './analysis.component.html'
})
export class AnalysisComponent implements OnInit, OnDestroy {
  analyses: IAnalysis[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected analysisService: AnalysisService,
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
      this.analysisService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IAnalysis[]>) => (this.analyses = res.body));
      return;
    }
    this.analysisService.query().subscribe((res: HttpResponse<IAnalysis[]>) => {
      this.analyses = res.body;
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
    this.registerChangeInAnalyses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAnalysis) {
    return item.id;
  }

  registerChangeInAnalyses() {
    this.eventSubscriber = this.eventManager.subscribe('analysisListModification', () => this.loadAll());
  }

  delete(analysis: IAnalysis) {
    const modalRef = this.modalService.open(AnalysisDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.analysis = analysis;
  }
}
