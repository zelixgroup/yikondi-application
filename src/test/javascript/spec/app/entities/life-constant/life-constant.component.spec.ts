import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { LifeConstantComponent } from 'app/entities/life-constant/life-constant.component';
import { LifeConstantService } from 'app/entities/life-constant/life-constant.service';
import { LifeConstant } from 'app/shared/model/life-constant.model';

describe('Component Tests', () => {
  describe('LifeConstant Management Component', () => {
    let comp: LifeConstantComponent;
    let fixture: ComponentFixture<LifeConstantComponent>;
    let service: LifeConstantService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [LifeConstantComponent],
        providers: []
      })
        .overrideTemplate(LifeConstantComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LifeConstantComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LifeConstantService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LifeConstant(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lifeConstants[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
