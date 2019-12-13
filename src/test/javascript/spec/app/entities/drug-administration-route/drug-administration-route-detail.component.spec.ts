import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { DrugAdministrationRouteDetailComponent } from 'app/entities/drug-administration-route/drug-administration-route-detail.component';
import { DrugAdministrationRoute } from 'app/shared/model/drug-administration-route.model';

describe('Component Tests', () => {
  describe('DrugAdministrationRoute Management Detail Component', () => {
    let comp: DrugAdministrationRouteDetailComponent;
    let fixture: ComponentFixture<DrugAdministrationRouteDetailComponent>;
    const route = ({ data: of({ drugAdministrationRoute: new DrugAdministrationRoute(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DrugAdministrationRouteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DrugAdministrationRouteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DrugAdministrationRouteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.drugAdministrationRoute).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
