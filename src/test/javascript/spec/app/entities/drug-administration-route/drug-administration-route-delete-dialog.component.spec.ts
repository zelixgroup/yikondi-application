import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { DrugAdministrationRouteDeleteDialogComponent } from 'app/entities/drug-administration-route/drug-administration-route-delete-dialog.component';
import { DrugAdministrationRouteService } from 'app/entities/drug-administration-route/drug-administration-route.service';

describe('Component Tests', () => {
  describe('DrugAdministrationRoute Management Delete Component', () => {
    let comp: DrugAdministrationRouteDeleteDialogComponent;
    let fixture: ComponentFixture<DrugAdministrationRouteDeleteDialogComponent>;
    let service: DrugAdministrationRouteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DrugAdministrationRouteDeleteDialogComponent]
      })
        .overrideTemplate(DrugAdministrationRouteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DrugAdministrationRouteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DrugAdministrationRouteService);
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
