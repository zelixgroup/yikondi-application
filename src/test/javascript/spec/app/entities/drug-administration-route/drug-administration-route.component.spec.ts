import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { DrugAdministrationRouteComponent } from 'app/entities/drug-administration-route/drug-administration-route.component';
import { DrugAdministrationRouteService } from 'app/entities/drug-administration-route/drug-administration-route.service';
import { DrugAdministrationRoute } from 'app/shared/model/drug-administration-route.model';

describe('Component Tests', () => {
  describe('DrugAdministrationRoute Management Component', () => {
    let comp: DrugAdministrationRouteComponent;
    let fixture: ComponentFixture<DrugAdministrationRouteComponent>;
    let service: DrugAdministrationRouteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DrugAdministrationRouteComponent],
        providers: []
      })
        .overrideTemplate(DrugAdministrationRouteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DrugAdministrationRouteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DrugAdministrationRouteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DrugAdministrationRoute(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.drugAdministrationRoutes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
