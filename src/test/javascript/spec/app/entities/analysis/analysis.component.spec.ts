import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { AnalysisComponent } from 'app/entities/analysis/analysis.component';
import { AnalysisService } from 'app/entities/analysis/analysis.service';
import { Analysis } from 'app/shared/model/analysis.model';

describe('Component Tests', () => {
  describe('Analysis Management Component', () => {
    let comp: AnalysisComponent;
    let fixture: ComponentFixture<AnalysisComponent>;
    let service: AnalysisService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [AnalysisComponent],
        providers: []
      })
        .overrideTemplate(AnalysisComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnalysisComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnalysisService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Analysis(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.analyses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
