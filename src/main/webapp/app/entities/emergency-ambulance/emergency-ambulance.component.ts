import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmergencyAmbulance } from 'app/shared/model/emergency-ambulance.model';
import { EmergencyAmbulanceService } from './emergency-ambulance.service';
import { EmergencyAmbulanceDeleteDialogComponent } from './emergency-ambulance-delete-dialog.component';

@Component({
  selector: 'jhi-emergency-ambulance',
  templateUrl: './emergency-ambulance.component.html'
})
export class EmergencyAmbulanceComponent implements OnInit, OnDestroy {
  emergencyAmbulances: IEmergencyAmbulance[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected emergencyAmbulanceService: EmergencyAmbulanceService,
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
      this.emergencyAmbulanceService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IEmergencyAmbulance[]>) => (this.emergencyAmbulances = res.body));
      return;
    }
    this.emergencyAmbulanceService.query().subscribe((res: HttpResponse<IEmergencyAmbulance[]>) => {
      this.emergencyAmbulances = res.body;
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
    this.registerChangeInEmergencyAmbulances();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEmergencyAmbulance) {
    return item.id;
  }

  registerChangeInEmergencyAmbulances() {
    this.eventSubscriber = this.eventManager.subscribe('emergencyAmbulanceListModification', () => this.loadAll());
  }

  delete(emergencyAmbulance: IEmergencyAmbulance) {
    const modalRef = this.modalService.open(EmergencyAmbulanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.emergencyAmbulance = emergencyAmbulance;
  }
}
