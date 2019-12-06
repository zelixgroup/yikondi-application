import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { HealthCentreDetailComponent } from 'app/entities/health-centre/health-centre-detail.component';
import { HealthCentre } from 'app/shared/model/health-centre.model';

describe('Component Tests', () => {
  describe('HealthCentre Management Detail Component', () => {
    let comp: HealthCentreDetailComponent;
    let fixture: ComponentFixture<HealthCentreDetailComponent>;
    const route = ({ data: of({ healthCentre: new HealthCentre(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [HealthCentreDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HealthCentreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HealthCentreDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.healthCentre).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
