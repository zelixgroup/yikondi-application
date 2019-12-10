import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILifeConstant } from 'app/shared/model/life-constant.model';
import { LifeConstantService } from './life-constant.service';
import { LifeConstantDeleteDialogComponent } from './life-constant-delete-dialog.component';

@Component({
  selector: 'jhi-life-constant',
  templateUrl: './life-constant.component.html'
})
export class LifeConstantComponent implements OnInit, OnDestroy {
  lifeConstants: ILifeConstant[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected lifeConstantService: LifeConstantService,
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
      this.lifeConstantService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ILifeConstant[]>) => (this.lifeConstants = res.body));
      return;
    }
    this.lifeConstantService.query().subscribe((res: HttpResponse<ILifeConstant[]>) => {
      this.lifeConstants = res.body;
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
    this.registerChangeInLifeConstants();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILifeConstant) {
    return item.id;
  }

  registerChangeInLifeConstants() {
    this.eventSubscriber = this.eventManager.subscribe('lifeConstantListModification', () => this.loadAll());
  }

  delete(lifeConstant: ILifeConstant) {
    const modalRef = this.modalService.open(LifeConstantDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.lifeConstant = lifeConstant;
  }
}
