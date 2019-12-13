import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInsuranceType } from 'app/shared/model/insurance-type.model';
import { InsuranceTypeService } from './insurance-type.service';
import { InsuranceTypeDeleteDialogComponent } from './insurance-type-delete-dialog.component';

@Component({
  selector: 'jhi-insurance-type',
  templateUrl: './insurance-type.component.html'
})
export class InsuranceTypeComponent implements OnInit, OnDestroy {
  insuranceTypes: IInsuranceType[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected insuranceTypeService: InsuranceTypeService,
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
      this.insuranceTypeService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IInsuranceType[]>) => (this.insuranceTypes = res.body));
      return;
    }
    this.insuranceTypeService.query().subscribe((res: HttpResponse<IInsuranceType[]>) => {
      this.insuranceTypes = res.body;
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
    this.registerChangeInInsuranceTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInsuranceType) {
    return item.id;
  }

  registerChangeInInsuranceTypes() {
    this.eventSubscriber = this.eventManager.subscribe('insuranceTypeListModification', () => this.loadAll());
  }

  delete(insuranceType: IInsuranceType) {
    const modalRef = this.modalService.open(InsuranceTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.insuranceType = insuranceType;
  }
}
