import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { InsuranceTypeComponent } from 'app/entities/insurance-type/insurance-type.component';
import { InsuranceTypeService } from 'app/entities/insurance-type/insurance-type.service';
import { InsuranceType } from 'app/shared/model/insurance-type.model';

describe('Component Tests', () => {
  describe('InsuranceType Management Component', () => {
    let comp: InsuranceTypeComponent;
    let fixture: ComponentFixture<InsuranceTypeComponent>;
    let service: InsuranceTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [InsuranceTypeComponent],
        providers: []
      })
        .overrideTemplate(InsuranceTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InsuranceTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InsuranceTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InsuranceType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.insuranceTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
