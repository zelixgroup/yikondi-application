import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { LifeConstantDetailComponent } from 'app/entities/life-constant/life-constant-detail.component';
import { LifeConstant } from 'app/shared/model/life-constant.model';

describe('Component Tests', () => {
  describe('LifeConstant Management Detail Component', () => {
    let comp: LifeConstantDetailComponent;
    let fixture: ComponentFixture<LifeConstantDetailComponent>;
    const route = ({ data: of({ lifeConstant: new LifeConstant(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [LifeConstantDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LifeConstantDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LifeConstantDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lifeConstant).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
