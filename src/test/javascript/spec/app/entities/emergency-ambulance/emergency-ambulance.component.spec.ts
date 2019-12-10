import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { EmergencyAmbulanceComponent } from 'app/entities/emergency-ambulance/emergency-ambulance.component';
import { EmergencyAmbulanceService } from 'app/entities/emergency-ambulance/emergency-ambulance.service';
import { EmergencyAmbulance } from 'app/shared/model/emergency-ambulance.model';

describe('Component Tests', () => {
  describe('EmergencyAmbulance Management Component', () => {
    let comp: EmergencyAmbulanceComponent;
    let fixture: ComponentFixture<EmergencyAmbulanceComponent>;
    let service: EmergencyAmbulanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [EmergencyAmbulanceComponent],
        providers: []
      })
        .overrideTemplate(EmergencyAmbulanceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmergencyAmbulanceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmergencyAmbulanceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EmergencyAmbulance(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.emergencyAmbulances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
