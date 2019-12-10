import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { AllergyDeleteDialogComponent } from 'app/entities/allergy/allergy-delete-dialog.component';
import { AllergyService } from 'app/entities/allergy/allergy.service';

describe('Component Tests', () => {
  describe('Allergy Management Delete Component', () => {
    let comp: AllergyDeleteDialogComponent;
    let fixture: ComponentFixture<AllergyDeleteDialogComponent>;
    let service: AllergyService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [AllergyDeleteDialogComponent]
      })
        .overrideTemplate(AllergyDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllergyDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllergyService);
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
