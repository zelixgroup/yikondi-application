import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHealthCentreDoctor } from 'app/shared/model/health-centre-doctor.model';
import { HealthCentreDoctorService } from './health-centre-doctor.service';
import { HealthCentreDoctorDeleteDialogComponent } from './health-centre-doctor-delete-dialog.component';

@Component({
  selector: 'jhi-health-centre-doctor',
  templateUrl: './health-centre-doctor.component.html'
})
export class HealthCentreDoctorComponent implements OnInit, OnDestroy {
  healthCentreDoctors: IHealthCentreDoctor[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected healthCentreDoctorService: HealthCentreDoctorService,
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
      this.healthCentreDoctorService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IHealthCentreDoctor[]>) => (this.healthCentreDoctors = res.body));
      return;
    }
    this.healthCentreDoctorService.query().subscribe((res: HttpResponse<IHealthCentreDoctor[]>) => {
      this.healthCentreDoctors = res.body;
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
    this.registerChangeInHealthCentreDoctors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IHealthCentreDoctor) {
    return item.id;
  }

  registerChangeInHealthCentreDoctors() {
    this.eventSubscriber = this.eventManager.subscribe('healthCentreDoctorListModification', () => this.loadAll());
  }

  delete(healthCentreDoctor: IHealthCentreDoctor) {
    const modalRef = this.modalService.open(HealthCentreDoctorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.healthCentreDoctor = healthCentreDoctor;
  }
}
