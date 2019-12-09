import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { LifeConstantDeleteDialogComponent } from 'app/entities/life-constant/life-constant-delete-dialog.component';
import { LifeConstantService } from 'app/entities/life-constant/life-constant.service';

describe('Component Tests', () => {
  describe('LifeConstant Management Delete Component', () => {
    let comp: LifeConstantDeleteDialogComponent;
    let fixture: ComponentFixture<LifeConstantDeleteDialogComponent>;
    let service: LifeConstantService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [LifeConstantDeleteDialogComponent]
      })
        .overrideTemplate(LifeConstantDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LifeConstantDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LifeConstantService);
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
