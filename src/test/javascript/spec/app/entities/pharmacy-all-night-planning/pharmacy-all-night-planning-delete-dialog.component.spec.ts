import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { PharmacyAllNightPlanningDeleteDialogComponent } from 'app/entities/pharmacy-all-night-planning/pharmacy-all-night-planning-delete-dialog.component';
import { PharmacyAllNightPlanningService } from 'app/entities/pharmacy-all-night-planning/pharmacy-all-night-planning.service';

describe('Component Tests', () => {
  describe('PharmacyAllNightPlanning Management Delete Component', () => {
    let comp: PharmacyAllNightPlanningDeleteDialogComponent;
    let fixture: ComponentFixture<PharmacyAllNightPlanningDeleteDialogComponent>;
    let service: PharmacyAllNightPlanningService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PharmacyAllNightPlanningDeleteDialogComponent]
      })
        .overrideTemplate(PharmacyAllNightPlanningDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PharmacyAllNightPlanningDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PharmacyAllNightPlanningService);
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
