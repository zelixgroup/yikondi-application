import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { AllergyComponent } from 'app/entities/allergy/allergy.component';
import { AllergyService } from 'app/entities/allergy/allergy.service';
import { Allergy } from 'app/shared/model/allergy.model';

describe('Component Tests', () => {
  describe('Allergy Management Component', () => {
    let comp: AllergyComponent;
    let fixture: ComponentFixture<AllergyComponent>;
    let service: AllergyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [AllergyComponent],
        providers: []
      })
        .overrideTemplate(AllergyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AllergyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllergyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Allergy(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.allergies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
