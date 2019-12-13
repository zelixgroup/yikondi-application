import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { MedicalPrescriptionDrugUpdateComponent } from 'app/entities/medical-prescription-drug/medical-prescription-drug-update.component';
import { MedicalPrescriptionDrugService } from 'app/entities/medical-prescription-drug/medical-prescription-drug.service';
import { MedicalPrescriptionDrug } from 'app/shared/model/medical-prescription-drug.model';

describe('Component Tests', () => {
  describe('MedicalPrescriptionDrug Management Update Component', () => {
    let comp: MedicalPrescriptionDrugUpdateComponent;
    let fixture: ComponentFixture<MedicalPrescriptionDrugUpdateComponent>;
    let service: MedicalPrescriptionDrugService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [MedicalPrescriptionDrugUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MedicalPrescriptionDrugUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicalPrescriptionDrugUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalPrescriptionDrugService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MedicalPrescriptionDrug(123);
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
        const entity = new MedicalPrescriptionDrug();
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
