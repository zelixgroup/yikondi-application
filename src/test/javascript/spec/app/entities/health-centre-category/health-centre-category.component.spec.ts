import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { HealthCentreCategoryComponent } from 'app/entities/health-centre-category/health-centre-category.component';
import { HealthCentreCategoryService } from 'app/entities/health-centre-category/health-centre-category.service';
import { HealthCentreCategory } from 'app/shared/model/health-centre-category.model';

describe('Component Tests', () => {
  describe('HealthCentreCategory Management Component', () => {
    let comp: HealthCentreCategoryComponent;
    let fixture: ComponentFixture<HealthCentreCategoryComponent>;
    let service: HealthCentreCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [HealthCentreCategoryComponent],
        providers: []
      })
        .overrideTemplate(HealthCentreCategoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HealthCentreCategoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HealthCentreCategoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new HealthCentreCategory(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.healthCentreCategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
