import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { LifeConstantUnitDetailComponent } from 'app/entities/life-constant-unit/life-constant-unit-detail.component';
import { LifeConstantUnit } from 'app/shared/model/life-constant-unit.model';

describe('Component Tests', () => {
  describe('LifeConstantUnit Management Detail Component', () => {
    let comp: LifeConstantUnitDetailComponent;
    let fixture: ComponentFixture<LifeConstantUnitDetailComponent>;
    const route = ({ data: of({ lifeConstantUnit: new LifeConstantUnit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [LifeConstantUnitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LifeConstantUnitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LifeConstantUnitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lifeConstantUnit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
