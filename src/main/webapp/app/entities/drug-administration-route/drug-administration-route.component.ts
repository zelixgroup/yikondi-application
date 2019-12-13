import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDrugAdministrationRoute } from 'app/shared/model/drug-administration-route.model';
import { DrugAdministrationRouteService } from './drug-administration-route.service';
import { DrugAdministrationRouteDeleteDialogComponent } from './drug-administration-route-delete-dialog.component';

@Component({
  selector: 'jhi-drug-administration-route',
  templateUrl: './drug-administration-route.component.html'
})
export class DrugAdministrationRouteComponent implements OnInit, OnDestroy {
  drugAdministrationRoutes: IDrugAdministrationRoute[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected drugAdministrationRouteService: DrugAdministrationRouteService,
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
      this.drugAdministrationRouteService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IDrugAdministrationRoute[]>) => (this.drugAdministrationRoutes = res.body));
      return;
    }
    this.drugAdministrationRouteService.query().subscribe((res: HttpResponse<IDrugAdministrationRoute[]>) => {
      this.drugAdministrationRoutes = res.body;
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
    this.registerChangeInDrugAdministrationRoutes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDrugAdministrationRoute) {
    return item.id;
  }

  registerChangeInDrugAdministrationRoutes() {
    this.eventSubscriber = this.eventManager.subscribe('drugAdministrationRouteListModification', () => this.loadAll());
  }

  delete(drugAdministrationRoute: IDrugAdministrationRoute) {
    const modalRef = this.modalService.open(DrugAdministrationRouteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.drugAdministrationRoute = drugAdministrationRoute;
  }
}
