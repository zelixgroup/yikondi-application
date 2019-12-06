import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PharmacyAllNightPlanningDetailComponent } from 'app/entities/pharmacy-all-night-planning/pharmacy-all-night-planning-detail.component';
import { PharmacyAllNightPlanning } from 'app/shared/model/pharmacy-all-night-planning.model';

describe('Component Tests', () => {
  describe('PharmacyAllNightPlanning Management Detail Component', () => {
    let comp: PharmacyAllNightPlanningDetailComponent;
    let fixture: ComponentFixture<PharmacyAllNightPlanningDetailComponent>;
    const route = ({ data: of({ pharmacyAllNightPlanning: new PharmacyAllNightPlanning(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PharmacyAllNightPlanningDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PharmacyAllNightPlanningDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PharmacyAllNightPlanningDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pharmacyAllNightPlanning).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
