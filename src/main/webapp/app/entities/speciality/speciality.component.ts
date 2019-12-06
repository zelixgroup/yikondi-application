import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISpeciality } from 'app/shared/model/speciality.model';
import { SpecialityService } from './speciality.service';
import { SpecialityDeleteDialogComponent } from './speciality-delete-dialog.component';

@Component({
  selector: 'jhi-speciality',
  templateUrl: './speciality.component.html'
})
export class SpecialityComponent implements OnInit, OnDestroy {
  specialities: ISpeciality[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected specialityService: SpecialityService,
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
      this.specialityService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ISpeciality[]>) => (this.specialities = res.body));
      return;
    }
    this.specialityService.query().subscribe((res: HttpResponse<ISpeciality[]>) => {
      this.specialities = res.body;
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
    this.registerChangeInSpecialities();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISpeciality) {
    return item.id;
  }

  registerChangeInSpecialities() {
    this.eventSubscriber = this.eventManager.subscribe('specialityListModification', () => this.loadAll());
  }

  delete(speciality: ISpeciality) {
    const modalRef = this.modalService.open(SpecialityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.speciality = speciality;
  }
}
