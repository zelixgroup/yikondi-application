import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PatientInsuranceCoverageUpdateComponent } from 'app/entities/patient-insurance-coverage/patient-insurance-coverage-update.component';
import { PatientInsuranceCoverageService } from 'app/entities/patient-insurance-coverage/patient-insurance-coverage.service';
import { PatientInsuranceCoverage } from 'app/shared/model/patient-insurance-coverage.model';

describe('Component Tests', () => {
  describe('PatientInsuranceCoverage Management Update Component', () => {
    let comp: PatientInsuranceCoverageUpdateComponent;
    let fixture: ComponentFixture<PatientInsuranceCoverageUpdateComponent>;
    let service: PatientInsuranceCoverageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientInsuranceCoverageUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PatientInsuranceCoverageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatientInsuranceCoverageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientInsuranceCoverageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PatientInsuranceCoverage(123);
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
        const entity = new PatientInsuranceCoverage();
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
