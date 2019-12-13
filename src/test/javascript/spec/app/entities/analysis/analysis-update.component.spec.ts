import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { AnalysisUpdateComponent } from 'app/entities/analysis/analysis-update.component';
import { AnalysisService } from 'app/entities/analysis/analysis.service';
import { Analysis } from 'app/shared/model/analysis.model';

describe('Component Tests', () => {
  describe('Analysis Management Update Component', () => {
    let comp: AnalysisUpdateComponent;
    let fixture: ComponentFixture<AnalysisUpdateComponent>;
    let service: AnalysisService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [AnalysisUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnalysisUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnalysisUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnalysisService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Analysis(123);
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
        const entity = new Analysis();
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
