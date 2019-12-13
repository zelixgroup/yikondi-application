import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnalysis } from 'app/shared/model/analysis.model';
import { AnalysisService } from './analysis.service';

@Component({
  templateUrl: './analysis-delete-dialog.component.html'
})
export class AnalysisDeleteDialogComponent {
  analysis: IAnalysis;

  constructor(protected analysisService: AnalysisService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.analysisService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'analysisListModification',
        content: 'Deleted an analysis'
      });
      this.activeModal.dismiss(true);
    });
  }
}
