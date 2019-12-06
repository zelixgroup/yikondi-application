import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { HealthCentreDeleteDialogComponent } from 'app/entities/health-centre/health-centre-delete-dialog.component';
import { HealthCentreService } from 'app/entities/health-centre/health-centre.service';

describe('Component Tests', () => {
  describe('HealthCentre Management Delete Component', () => {
    let comp: HealthCentreDeleteDialogComponent;
    let fixture: ComponentFixture<HealthCentreDeleteDialogComponent>;
    let service: HealthCentreService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [HealthCentreDeleteDialogComponent]
      })
        .overrideTemplate(HealthCentreDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HealthCentreDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HealthCentreService);
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
