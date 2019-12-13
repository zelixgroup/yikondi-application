import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { InsuranceTypeDetailComponent } from 'app/entities/insurance-type/insurance-type-detail.component';
import { InsuranceType } from 'app/shared/model/insurance-type.model';

describe('Component Tests', () => {
  describe('InsuranceType Management Detail Component', () => {
    let comp: InsuranceTypeDetailComponent;
    let fixture: ComponentFixture<InsuranceTypeDetailComponent>;
    const route = ({ data: of({ insuranceType: new InsuranceType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [InsuranceTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InsuranceTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InsuranceTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.insuranceType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
