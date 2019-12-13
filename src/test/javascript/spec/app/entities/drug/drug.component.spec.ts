import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { DrugComponent } from 'app/entities/drug/drug.component';
import { DrugService } from 'app/entities/drug/drug.service';
import { Drug } from 'app/shared/model/drug.model';

describe('Component Tests', () => {
  describe('Drug Management Component', () => {
    let comp: DrugComponent;
    let fixture: ComponentFixture<DrugComponent>;
    let service: DrugService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DrugComponent],
        providers: []
      })
        .overrideTemplate(DrugComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DrugComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DrugService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Drug(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.drugs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
