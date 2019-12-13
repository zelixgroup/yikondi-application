import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { MedicalRecordAuthorizationUpdateComponent } from 'app/entities/medical-record-authorization/medical-record-authorization-update.component';
import { MedicalRecordAuthorizationService } from 'app/entities/medical-record-authorization/medical-record-authorization.service';
import { MedicalRecordAuthorization } from 'app/shared/model/medical-record-authorization.model';

describe('Component Tests', () => {
  describe('MedicalRecordAuthorization Management Update Component', () => {
    let comp: MedicalRecordAuthorizationUpdateComponent;
    let fixture: ComponentFixture<MedicalRecordAuthorizationUpdateComponent>;
    let service: MedicalRecordAuthorizationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [MedicalRecordAuthorizationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MedicalRecordAuthorizationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicalRecordAuthorizationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalRecordAuthorizationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MedicalRecordAuthorization(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MedicalRecordAuthorization();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
