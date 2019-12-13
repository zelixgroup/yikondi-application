import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { DrugDosageFormComponent } from 'app/entities/drug-dosage-form/drug-dosage-form.component';
import { DrugDosageFormService } from 'app/entities/drug-dosage-form/drug-dosage-form.service';
import { DrugDosageForm } from 'app/shared/model/drug-dosage-form.model';

describe('Component Tests', () => {
  describe('DrugDosageForm Management Component', () => {
    let comp: DrugDosageFormComponent;
    let fixture: ComponentFixture<DrugDosageFormComponent>;
    let service: DrugDosageFormService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DrugDosageFormComponent],
        providers: []
      })
        .overrideTemplate(DrugDosageFormComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DrugDosageFormComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DrugDosageFormService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DrugDosageForm(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.drugDosageForms[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
