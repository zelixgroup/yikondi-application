import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { MedicalPrescriptionAnalysisUpdateComponent } from 'app/entities/medical-prescription-analysis/medical-prescription-analysis-update.component';
import { MedicalPrescriptionAnalysisService } from 'app/entities/medical-prescription-analysis/medical-prescription-analysis.service';
import { MedicalPrescriptionAnalysis } from 'app/shared/model/medical-prescription-analysis.model';

describe('Component Tests', () => {
  describe('MedicalPrescriptionAnalysis Management Update Component', () => {
    let comp: MedicalPrescriptionAnalysisUpdateComponent;
    let fixture: ComponentFixture<MedicalPrescriptionAnalysisUpdateComponent>;
    let service: MedicalPrescriptionAnalysisService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [MedicalPrescriptionAnalysisUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MedicalPrescriptionAnalysisUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicalPrescriptionAnalysisUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalPrescriptionAnalysisService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MedicalPrescriptionAnalysis(123);
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
        const entity = new MedicalPrescriptionAnalysis();
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
