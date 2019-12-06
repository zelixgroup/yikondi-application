import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { DoctorWorkingSlotComponent } from 'app/entities/doctor-working-slot/doctor-working-slot.component';
import { DoctorWorkingSlotService } from 'app/entities/doctor-working-slot/doctor-working-slot.service';
import { DoctorWorkingSlot } from 'app/shared/model/doctor-working-slot.model';

describe('Component Tests', () => {
  describe('DoctorWorkingSlot Management Component', () => {
    let comp: DoctorWorkingSlotComponent;
    let fixture: ComponentFixture<DoctorWorkingSlotComponent>;
    let service: DoctorWorkingSlotService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DoctorWorkingSlotComponent],
        providers: []
      })
        .overrideTemplate(DoctorWorkingSlotComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoctorWorkingSlotComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoctorWorkingSlotService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DoctorWorkingSlot(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.doctorWorkingSlots[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
