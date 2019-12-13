import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { InsuranceTypeDeleteDialogComponent } from 'app/entities/insurance-type/insurance-type-delete-dialog.component';
import { InsuranceTypeService } from 'app/entities/insurance-type/insurance-type.service';

describe('Component Tests', () => {
  describe('InsuranceType Management Delete Component', () => {
    let comp: InsuranceTypeDeleteDialogComponent;
    let fixture: ComponentFixture<InsuranceTypeDeleteDialogComponent>;
    let service: InsuranceTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [InsuranceTypeDeleteDialogComponent]
      })
        .overrideTemplate(InsuranceTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InsuranceTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InsuranceTypeService);
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
