import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { FamilyRelationshipComponent } from 'app/entities/family-relationship/family-relationship.component';
import { FamilyRelationshipService } from 'app/entities/family-relationship/family-relationship.service';
import { FamilyRelationship } from 'app/shared/model/family-relationship.model';

describe('Component Tests', () => {
  describe('FamilyRelationship Management Component', () => {
    let comp: FamilyRelationshipComponent;
    let fixture: ComponentFixture<FamilyRelationshipComponent>;
    let service: FamilyRelationshipService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [FamilyRelationshipComponent],
        providers: []
      })
        .overrideTemplate(FamilyRelationshipComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FamilyRelationshipComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FamilyRelationshipService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FamilyRelationship(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.familyRelationships[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
