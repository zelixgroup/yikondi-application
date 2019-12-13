import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { MedicalRecordAuthorizationComponent } from 'app/entities/medical-record-authorization/medical-record-authorization.component';
import { MedicalRecordAuthorizationService } from 'app/entities/medical-record-authorization/medical-record-authorization.service';
import { MedicalRecordAuthorization } from 'app/shared/model/medical-record-authorization.model';

describe('Component Tests', () => {
  describe('MedicalRecordAuthorization Management Component', () => {
    let comp: MedicalRecordAuthorizationComponent;
    let fixture: ComponentFixture<MedicalRecordAuthorizationComponent>;
    let service: MedicalRecordAuthorizationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [MedicalRecordAuthorizationComponent],
        providers: []
      })
        .overrideTemplate(MedicalRecordAuthorizationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicalRecordAuthorizationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalRecordAuthorizationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MedicalRecordAuthorization(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.medicalRecordAuthorizations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
