import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { HolidayDetailComponent } from 'app/entities/holiday/holiday-detail.component';
import { Holiday } from 'app/shared/model/holiday.model';

describe('Component Tests', () => {
  describe('Holiday Management Detail Component', () => {
    let comp: HolidayDetailComponent;
    let fixture: ComponentFixture<HolidayDetailComponent>;
    const route = ({ data: of({ holiday: new Holiday(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [HolidayDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HolidayDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HolidayDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.holiday).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
