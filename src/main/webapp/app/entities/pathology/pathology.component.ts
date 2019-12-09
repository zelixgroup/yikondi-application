import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPathology } from 'app/shared/model/pathology.model';
import { PathologyService } from './pathology.service';
import { PathologyDeleteDialogComponent } from './pathology-delete-dialog.component';

@Component({
  selector: 'jhi-pathology',
  templateUrl: './pathology.component.html'
})
export class PathologyComponent implements OnInit, OnDestroy {
  pathologies: IPathology[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected pathologyService: PathologyService,
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
      this.pathologyService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IPathology[]>) => (this.pathologies = res.body));
      return;
    }
    this.pathologyService.query().subscribe((res: HttpResponse<IPathology[]>) => {
      this.pathologies = res.body;
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
    this.registerChangeInPathologies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPathology) {
    return item.id;
  }

  registerChangeInPathologies() {
    this.eventSubscriber = this.eventManager.subscribe('pathologyListModification', () => this.loadAll());
  }

  delete(pathology: IPathology) {
    const modalRef = this.modalService.open(PathologyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pathology = pathology;
  }
}
