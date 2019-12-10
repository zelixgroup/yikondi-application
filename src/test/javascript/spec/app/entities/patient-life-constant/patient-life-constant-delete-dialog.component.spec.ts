import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { PatientLifeConstantDeleteDialogComponent } from 'app/entities/patient-life-constant/patient-life-constant-delete-dialog.component';
import { PatientLifeConstantService } from 'app/entities/patient-life-constant/patient-life-constant.service';

describe('Component Tests', () => {
  describe('PatientLifeConstant Management Delete Component', () => {
    let comp: PatientLifeConstantDeleteDialogComponent;
    let fixture: ComponentFixture<PatientLifeConstantDeleteDialogComponent>;
    let service: PatientLifeConstantService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientLifeConstantDeleteDialogComponent]
      })
        .overrideTemplate(PatientLifeConstantDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PatientLifeConstantDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientLifeConstantService);
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
