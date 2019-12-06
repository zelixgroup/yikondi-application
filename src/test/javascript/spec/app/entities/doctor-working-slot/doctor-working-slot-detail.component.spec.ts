import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { DoctorWorkingSlotDetailComponent } from 'app/entities/doctor-working-slot/doctor-working-slot-detail.component';
import { DoctorWorkingSlot } from 'app/shared/model/doctor-working-slot.model';

describe('Component Tests', () => {
  describe('DoctorWorkingSlot Management Detail Component', () => {
    let comp: DoctorWorkingSlotDetailComponent;
    let fixture: ComponentFixture<DoctorWorkingSlotDetailComponent>;
    const route = ({ data: of({ doctorWorkingSlot: new DoctorWorkingSlot(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [DoctorWorkingSlotDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DoctorWorkingSlotDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoctorWorkingSlotDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.doctorWorkingSlot).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
