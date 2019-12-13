import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { FamilyRelationshipUpdateComponent } from 'app/entities/family-relationship/family-relationship-update.component';
import { FamilyRelationshipService } from 'app/entities/family-relationship/family-relationship.service';
import { FamilyRelationship } from 'app/shared/model/family-relationship.model';

describe('Component Tests', () => {
  describe('FamilyRelationship Management Update Component', () => {
    let comp: FamilyRelationshipUpdateComponent;
    let fixture: ComponentFixture<FamilyRelationshipUpdateComponent>;
    let service: FamilyRelationshipService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [FamilyRelationshipUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FamilyRelationshipUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FamilyRelationshipUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FamilyRelationshipService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FamilyRelationship(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new FamilyRelationship();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
