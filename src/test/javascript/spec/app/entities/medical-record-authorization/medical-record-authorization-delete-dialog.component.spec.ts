import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { MedicalRecordAuthorizationDeleteDialogComponent } from 'app/entities/medical-record-authorization/medical-record-authorization-delete-dialog.component';
import { MedicalRecordAuthorizationService } from 'app/entities/medical-record-authorization/medical-record-authorization.service';

describe('Component Tests', () => {
  describe('MedicalRecordAuthorization Management Delete Component', () => {
    let comp: MedicalRecordAuthorizationDeleteDialogComponent;
    let fixture: ComponentFixture<MedicalRecordAuthorizationDeleteDialogComponent>;
    let service: MedicalRecordAuthorizationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [MedicalRecordAuthorizationDeleteDialogComponent]
      })
        .overrideTemplate(MedicalRecordAuthorizationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicalRecordAuthorizationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalRecordAuthorizationService);
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
