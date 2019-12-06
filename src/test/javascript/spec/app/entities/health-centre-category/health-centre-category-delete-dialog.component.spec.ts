import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { HealthCentreCategoryDeleteDialogComponent } from 'app/entities/health-centre-category/health-centre-category-delete-dialog.component';
import { HealthCentreCategoryService } from 'app/entities/health-centre-category/health-centre-category.service';

describe('Component Tests', () => {
  describe('HealthCentreCategory Management Delete Component', () => {
    let comp: HealthCentreCategoryDeleteDialogComponent;
    let fixture: ComponentFixture<HealthCentreCategoryDeleteDialogComponent>;
    let service: HealthCentreCategoryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [HealthCentreCategoryDeleteDialogComponent]
      })
        .overrideTemplate(HealthCentreCategoryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HealthCentreCategoryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HealthCentreCategoryService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
