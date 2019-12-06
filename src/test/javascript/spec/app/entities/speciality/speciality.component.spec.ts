import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { SpecialityComponent } from 'app/entities/speciality/speciality.component';
import { SpecialityService } from 'app/entities/speciality/speciality.service';
import { Speciality } from 'app/shared/model/speciality.model';

describe('Component Tests', () => {
  describe('Speciality Management Component', () => {
    let comp: SpecialityComponent;
    let fixture: ComponentFixture<SpecialityComponent>;
    let service: SpecialityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [SpecialityComponent],
        providers: []
      })
        .overrideTemplate(SpecialityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpecialityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpecialityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Speciality(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.specialities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
