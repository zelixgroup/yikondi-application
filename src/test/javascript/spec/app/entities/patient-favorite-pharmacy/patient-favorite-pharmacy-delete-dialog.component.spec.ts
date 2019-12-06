import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YikondiTestModule } from '../../../test.module';
import { PatientFavoritePharmacyDeleteDialogComponent } from 'app/entities/patient-favorite-pharmacy/patient-favorite-pharmacy-delete-dialog.component';
import { PatientFavoritePharmacyService } from 'app/entities/patient-favorite-pharmacy/patient-favorite-pharmacy.service';

describe('Component Tests', () => {
  describe('PatientFavoritePharmacy Management Delete Component', () => {
    let comp: PatientFavoritePharmacyDeleteDialogComponent;
    let fixture: ComponentFixture<PatientFavoritePharmacyDeleteDialogComponent>;
    let service: PatientFavoritePharmacyService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientFavoritePharmacyDeleteDialogComponent]
      })
        .overrideTemplate(PatientFavoritePharmacyDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PatientFavoritePharmacyDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientFavoritePharmacyService);
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
