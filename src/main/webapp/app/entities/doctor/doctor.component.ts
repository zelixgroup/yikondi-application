import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDoctor } from 'app/shared/model/doctor.model';
import { DoctorService } from './doctor.service';
import { DoctorDeleteDialogComponent } from './doctor-delete-dialog.component';

@Component({
  selector: 'jhi-doctor',
  templateUrl: './doctor.component.html'
})
export class DoctorComponent implements OnInit, OnDestroy {
  doctors: IDoctor[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected doctorService: DoctorService,
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
      this.doctorService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IDoctor[]>) => (this.doctors = res.body));
      return;
    }
    this.doctorService.query().subscribe((res: HttpResponse<IDoctor[]>) => {
      this.doctors = res.body;
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
    this.registerChangeInDoctors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDoctor) {
    return item.id;
  }

  registerChangeInDoctors() {
    this.eventSubscriber = this.eventManager.subscribe('doctorListModification', () => this.loadAll());
  }

  delete(doctor: IDoctor) {
    const modalRef = this.modalService.open(DoctorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.doctor = doctor;
  }
}
