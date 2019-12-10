import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { PathologyComponent } from 'app/entities/pathology/pathology.component';
import { PathologyService } from 'app/entities/pathology/pathology.service';
import { Pathology } from 'app/shared/model/pathology.model';

describe('Component Tests', () => {
  describe('Pathology Management Component', () => {
    let comp: PathologyComponent;
    let fixture: ComponentFixture<PathologyComponent>;
    let service: PathologyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PathologyComponent],
        providers: []
      })
        .overrideTemplate(PathologyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PathologyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PathologyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Pathology(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pathologies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
