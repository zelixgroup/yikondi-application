import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPharmacy } from 'app/shared/model/pharmacy.model';
import { PharmacyService } from './pharmacy.service';
import { PharmacyDeleteDialogComponent } from './pharmacy-delete-dialog.component';

@Component({
  selector: 'jhi-pharmacy',
  templateUrl: './pharmacy.component.html'
})
export class PharmacyComponent implements OnInit, OnDestroy {
  pharmacies: IPharmacy[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected pharmacyService: PharmacyService,
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
      this.pharmacyService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IPharmacy[]>) => (this.pharmacies = res.body));
      return;
    }
    this.pharmacyService.query().subscribe((res: HttpResponse<IPharmacy[]>) => {
      this.pharmacies = res.body;
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
    this.registerChangeInPharmacies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPharmacy) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInPharmacies() {
    this.eventSubscriber = this.eventManager.subscribe('pharmacyListModification', () => this.loadAll());
  }

  delete(pharmacy: IPharmacy) {
    const modalRef = this.modalService.open(PharmacyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pharmacy = pharmacy;
  }
}
