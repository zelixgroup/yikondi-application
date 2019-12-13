import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { FamilyRelationshipDeleteDialogComponent } from 'app/entities/family-relationship/family-relationship-delete-dialog.component';
import { FamilyRelationshipService } from 'app/entities/family-relationship/family-relationship.service';

describe('Component Tests', () => {
  describe('FamilyRelationship Management Delete Component', () => {
    let comp: FamilyRelationshipDeleteDialogComponent;
    let fixture: ComponentFixture<FamilyRelationshipDeleteDialogComponent>;
    let service: FamilyRelationshipService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [FamilyRelationshipDeleteDialogComponent]
      })
        .overrideTemplate(FamilyRelationshipDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FamilyRelationshipDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FamilyRelationshipService);
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
