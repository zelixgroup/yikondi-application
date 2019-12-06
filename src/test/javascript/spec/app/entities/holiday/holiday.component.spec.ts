import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { HolidayComponent } from 'app/entities/holiday/holiday.component';
import { HolidayService } from 'app/entities/holiday/holiday.service';
import { Holiday } from 'app/shared/model/holiday.model';

describe('Component Tests', () => {
  describe('Holiday Management Component', () => {
    let comp: HolidayComponent;
    let fixture: ComponentFixture<HolidayComponent>;
    let service: HolidayService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [HolidayComponent],
        providers: []
      })
        .overrideTemplate(HolidayComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HolidayComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HolidayService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Holiday(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.holidays[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
