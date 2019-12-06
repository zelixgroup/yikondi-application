import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDoctorSchedule } from 'app/shared/model/doctor-schedule.model';
import { DoctorScheduleService } from './doctor-schedule.service';
import { DoctorScheduleDeleteDialogComponent } from './doctor-schedule-delete-dialog.component';

@Component({
  selector: 'jhi-doctor-schedule',
  templateUrl: './doctor-schedule.component.html'
})
export class DoctorScheduleComponent implements OnInit, OnDestroy {
  doctorSchedules: IDoctorSchedule[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected doctorScheduleService: DoctorScheduleService,
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
      this.doctorScheduleService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IDoctorSchedule[]>) => (this.doctorSchedules = res.body));
      return;
    }
    this.doctorScheduleService.query().subscribe((res: HttpResponse<IDoctorSchedule[]>) => {
      this.doctorSchedules = res.body;
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
    this.registerChangeInDoctorSchedules();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDoctorSchedule) {
    return item.id;
  }

  registerChangeInDoctorSchedules() {
    this.eventSubscriber = this.eventManager.subscribe('doctorScheduleListModification', () => this.loadAll());
  }

  delete(doctorSchedule: IDoctorSchedule) {
    const modalRef = this.modalService.open(DoctorScheduleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.doctorSchedule = doctorSchedule;
  }
}
