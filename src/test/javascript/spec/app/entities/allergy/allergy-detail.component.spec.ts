import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { AllergyDetailComponent } from 'app/entities/allergy/allergy-detail.component';
import { Allergy } from 'app/shared/model/allergy.model';

describe('Component Tests', () => {
  describe('Allergy Management Detail Component', () => {
    let comp: AllergyDetailComponent;
    let fixture: ComponentFixture<AllergyDetailComponent>;
    const route = ({ data: of({ allergy: new Allergy(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [AllergyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AllergyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllergyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.allergy).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
