import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { HealthCentreDoctorUpdateComponent } from 'app/entities/health-centre-doctor/health-centre-doctor-update.component';
import { HealthCentreDoctorService } from 'app/entities/health-centre-doctor/health-centre-doctor.service';
import { HealthCentreDoctor } from 'app/shared/model/health-centre-doctor.model';

describe('Component Tests', () => {
  describe('HealthCentreDoctor Management Update Component', () => {
    let comp: HealthCentreDoctorUpdateComponent;
    let fixture: ComponentFixture<HealthCentreDoctorUpdateComponent>;
    let service: HealthCentreDoctorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [HealthCentreDoctorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(HealthCentreDoctorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HealthCentreDoctorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HealthCentreDoctorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new HealthCentreDoctor(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new HealthCentreDoctor();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
