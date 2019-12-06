import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { HealthCentreComponent } from 'app/entities/health-centre/health-centre.component';
import { HealthCentreService } from 'app/entities/health-centre/health-centre.service';
import { HealthCentre } from 'app/shared/model/health-centre.model';

describe('Component Tests', () => {
  describe('HealthCentre Management Component', () => {
    let comp: HealthCentreComponent;
    let fixture: ComponentFixture<HealthCentreComponent>;
    let service: HealthCentreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [HealthCentreComponent],
        providers: []
      })
        .overrideTemplate(HealthCentreComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HealthCentreComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HealthCentreService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new HealthCentre(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.healthCentres[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
