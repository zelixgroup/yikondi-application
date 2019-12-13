import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { DrugDosageFormDetailComponent } from 'app/entities/drug-dosage-form/drug-dosage-form-detail.component';
import { DrugDosageForm } from 'app/shared/model/drug-dosage-form.model';

describe('Component Tests', () => {
  describe('DrugDosageForm Management Detail Component', () => {
    let comp: DrugDosageFormDetailComponent;
    let fixture: ComponentFixture<DrugDosageFormDetailComponent>;
    const route = ({ data: of({ drugDosageForm: new DrugDosageForm(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DrugDosageFormDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DrugDosageFormDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DrugDosageFormDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.drugDosageForm).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
