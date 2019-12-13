import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { DoctorAssistantComponent } from 'app/entities/doctor-assistant/doctor-assistant.component';
import { DoctorAssistantService } from 'app/entities/doctor-assistant/doctor-assistant.service';
import { DoctorAssistant } from 'app/shared/model/doctor-assistant.model';

describe('Component Tests', () => {
  describe('DoctorAssistant Management Component', () => {
    let comp: DoctorAssistantComponent;
    let fixture: ComponentFixture<DoctorAssistantComponent>;
    let service: DoctorAssistantService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DoctorAssistantComponent],
        providers: []
      })
        .overrideTemplate(DoctorAssistantComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoctorAssistantComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoctorAssistantService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DoctorAssistant(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.doctorAssistants[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
