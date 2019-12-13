import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFamilyRelationship } from 'app/shared/model/family-relationship.model';
import { FamilyRelationshipService } from './family-relationship.service';
import { FamilyRelationshipDeleteDialogComponent } from './family-relationship-delete-dialog.component';

@Component({
  selector: 'jhi-family-relationship',
  templateUrl: './family-relationship.component.html'
})
export class FamilyRelationshipComponent implements OnInit, OnDestroy {
  familyRelationships: IFamilyRelationship[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected familyRelationshipService: FamilyRelationshipService,
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
      this.familyRelationshipService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IFamilyRelationship[]>) => (this.familyRelationships = res.body));
      return;
    }
    this.familyRelationshipService.query().subscribe((res: HttpResponse<IFamilyRelationship[]>) => {
      this.familyRelationships = res.body;
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
    this.registerChangeInFamilyRelationships();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFamilyRelationship) {
    return item.id;
  }

  registerChangeInFamilyRelationships() {
    this.eventSubscriber = this.eventManager.subscribe('familyRelationshipListModification', () => this.loadAll());
  }

  delete(familyRelationship: IFamilyRelationship) {
    const modalRef = this.modalService.open(FamilyRelationshipDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.familyRelationship = familyRelationship;
  }
}
