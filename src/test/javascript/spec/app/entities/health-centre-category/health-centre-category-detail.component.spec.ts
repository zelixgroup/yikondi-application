import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { HealthCentreCategoryDetailComponent } from 'app/entities/health-centre-category/health-centre-category-detail.component';
import { HealthCentreCategory } from 'app/shared/model/health-centre-category.model';

describe('Component Tests', () => {
  describe('HealthCentreCategory Management Detail Component', () => {
    let comp: HealthCentreCategoryDetailComponent;
    let fixture: ComponentFixture<HealthCentreCategoryDetailComponent>;
    const route = ({ data: of({ healthCentreCategory: new HealthCentreCategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [HealthCentreCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HealthCentreCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HealthCentreCategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.healthCentreCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
