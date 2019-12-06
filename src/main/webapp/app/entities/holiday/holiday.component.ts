import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHoliday } from 'app/shared/model/holiday.model';
import { HolidayService } from './holiday.service';
import { HolidayDeleteDialogComponent } from './holiday-delete-dialog.component';

@Component({
  selector: 'jhi-holiday',
  templateUrl: './holiday.component.html'
})
export class HolidayComponent implements OnInit, OnDestroy {
  holidays: IHoliday[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected holidayService: HolidayService,
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
      this.holidayService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IHoliday[]>) => (this.holidays = res.body));
      return;
    }
    this.holidayService.query().subscribe((res: HttpResponse<IHoliday[]>) => {
      this.holidays = res.body;
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
    this.registerChangeInHolidays();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IHoliday) {
    return item.id;
  }

  registerChangeInHolidays() {
    this.eventSubscriber = this.eventManager.subscribe('holidayListModification', () => this.loadAll());
  }

  delete(holiday: IHoliday) {
    const modalRef = this.modalService.open(HolidayDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.holiday = holiday;
  }
}
