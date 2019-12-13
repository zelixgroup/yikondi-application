import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { FamilyRelationshipDetailComponent } from 'app/entities/family-relationship/family-relationship-detail.component';
import { FamilyRelationship } from 'app/shared/model/family-relationship.model';

describe('Component Tests', () => {
  describe('FamilyRelationship Management Detail Component', () => {
    let comp: FamilyRelationshipDetailComponent;
    let fixture: ComponentFixture<FamilyRelationshipDetailComponent>;
    const route = ({ data: of({ familyRelationship: new FamilyRelationship(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [FamilyRelationshipDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FamilyRelationshipDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FamilyRelationshipDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.familyRelationship).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
