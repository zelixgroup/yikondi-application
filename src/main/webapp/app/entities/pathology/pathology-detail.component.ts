import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPathology } from 'app/shared/model/pathology.model';

@Component({
  selector: 'jhi-pathology-detail',
  templateUrl: './pathology-detail.component.html'
})
export class PathologyDetailComponent implements OnInit {
  pathology: IPathology;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pathology }) => {
      this.pathology = pathology;
    });
  }

  previousState() {
    window.history.back();
  }
}
