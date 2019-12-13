import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { MedicalPrescriptionUpdateComponent } from 'app/entities/medical-prescription/medical-prescription-update.component';
import { MedicalPrescriptionService } from 'app/entities/medical-prescription/medical-prescription.service';
import { MedicalPrescription } from 'app/shared/model/medical-prescription.model';

describe('Component Tests', () => {
  describe('MedicalPrescription Management Update Component', () => {
    let comp: MedicalPrescriptionUpdateComponent;
    let fixture: ComponentFixture<MedicalPrescriptionUpdateComponent>;
    let service: MedicalPrescriptionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [MedicalPrescriptionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MedicalPrescriptionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicalPrescriptionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalPrescriptionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MedicalPrescription(123);
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
        const entity = new MedicalPrescription();
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
