import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { EmergencyAmbulanceDetailComponent } from 'app/entities/emergency-ambulance/emergency-ambulance-detail.component';
import { EmergencyAmbulance } from 'app/shared/model/emergency-ambulance.model';

describe('Component Tests', () => {
  describe('EmergencyAmbulance Management Detail Component', () => {
    let comp: EmergencyAmbulanceDetailComponent;
    let fixture: ComponentFixture<EmergencyAmbulanceDetailComponent>;
    const route = ({ data: of({ emergencyAmbulance: new EmergencyAmbulance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [EmergencyAmbulanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EmergencyAmbulanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmergencyAmbulanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emergencyAmbulance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
