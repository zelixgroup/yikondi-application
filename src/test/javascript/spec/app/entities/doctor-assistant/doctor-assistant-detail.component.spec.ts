import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { DoctorAssistantDetailComponent } from 'app/entities/doctor-assistant/doctor-assistant-detail.component';
import { DoctorAssistant } from 'app/shared/model/doctor-assistant.model';

describe('Component Tests', () => {
  describe('DoctorAssistant Management Detail Component', () => {
    let comp: DoctorAssistantDetailComponent;
    let fixture: ComponentFixture<DoctorAssistantDetailComponent>;
    const route = ({ data: of({ doctorAssistant: new DoctorAssistant(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DoctorAssistantDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DoctorAssistantDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoctorAssistantDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.doctorAssistant).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
