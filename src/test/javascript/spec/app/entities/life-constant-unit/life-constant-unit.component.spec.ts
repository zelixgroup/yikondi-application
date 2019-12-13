import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { LifeConstantUnitComponent } from 'app/entities/life-constant-unit/life-constant-unit.component';
import { LifeConstantUnitService } from 'app/entities/life-constant-unit/life-constant-unit.service';
import { LifeConstantUnit } from 'app/shared/model/life-constant-unit.model';

describe('Component Tests', () => {
  describe('LifeConstantUnit Management Component', () => {
    let comp: LifeConstantUnitComponent;
    let fixture: ComponentFixture<LifeConstantUnitComponent>;
    let service: LifeConstantUnitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [LifeConstantUnitComponent],
        providers: []
      })
        .overrideTemplate(LifeConstantUnitComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LifeConstantUnitComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LifeConstantUnitService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LifeConstantUnit(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lifeConstantUnits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
