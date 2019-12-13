import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { DrugDetailComponent } from 'app/entities/drug/drug-detail.component';
import { Drug } from 'app/shared/model/drug.model';

describe('Component Tests', () => {
  describe('Drug Management Detail Component', () => {
    let comp: DrugDetailComponent;
    let fixture: ComponentFixture<DrugDetailComponent>;
    const route = ({ data: of({ drug: new Drug(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DrugDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DrugDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DrugDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.drug).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
