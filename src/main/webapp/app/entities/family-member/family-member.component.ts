import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFamilyMember } from 'app/shared/model/family-member.model';
import { FamilyMemberService } from './family-member.service';
import { FamilyMemberDeleteDialogComponent } from './family-member-delete-dialog.component';

@Component({
  selector: 'jhi-family-member',
  templateUrl: './family-member.component.html'
})
export class FamilyMemberComponent implements OnInit, OnDestroy {
  familyMembers: IFamilyMember[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected familyMemberService: FamilyMemberService,
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
      this.familyMemberService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IFamilyMember[]>) => (this.familyMembers = res.body));
      return;
    }
    this.familyMemberService.query().subscribe((res: HttpResponse<IFamilyMember[]>) => {
      this.familyMembers = res.body;
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
    this.registerChangeInFamilyMembers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFamilyMember) {
    return item.id;
  }

  registerChangeInFamilyMembers() {
    this.eventSubscriber = this.eventManager.subscribe('familyMemberListModification', () => this.loadAll());
  }

  delete(familyMember: IFamilyMember) {
    const modalRef = this.modalService.open(FamilyMemberDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.familyMember = familyMember;
  }
}
