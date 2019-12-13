import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { LifeConstantUnitDeleteDialogComponent } from 'app/entities/life-constant-unit/life-constant-unit-delete-dialog.component';
import { LifeConstantUnitService } from 'app/entities/life-constant-unit/life-constant-unit.service';

describe('Component Tests', () => {
  describe('LifeConstantUnit Management Delete Component', () => {
    let comp: LifeConstantUnitDeleteDialogComponent;
    let fixture: ComponentFixture<LifeConstantUnitDeleteDialogComponent>;
    let service: LifeConstantUnitService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [LifeConstantUnitDeleteDialogComponent]
      })
        .overrideTemplate(LifeConstantUnitDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LifeConstantUnitDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LifeConstantUnitService);
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
