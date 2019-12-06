import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { PharmacyAllNightPlanningComponent } from 'app/entities/pharmacy-all-night-planning/pharmacy-all-night-planning.component';
import { PharmacyAllNightPlanningService } from 'app/entities/pharmacy-all-night-planning/pharmacy-all-night-planning.service';
import { PharmacyAllNightPlanning } from 'app/shared/model/pharmacy-all-night-planning.model';

describe('Component Tests', () => {
  describe('PharmacyAllNightPlanning Management Component', () => {
    let comp: PharmacyAllNightPlanningComponent;
    let fixture: ComponentFixture<PharmacyAllNightPlanningComponent>;
    let service: PharmacyAllNightPlanningService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PharmacyAllNightPlanningComponent],
        providers: []
      })
        .overrideTemplate(PharmacyAllNightPlanningComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PharmacyAllNightPlanningComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PharmacyAllNightPlanningService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PharmacyAllNightPlanning(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pharmacyAllNightPlannings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
