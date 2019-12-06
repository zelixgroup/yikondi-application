import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PatientFavoriteDoctorUpdateComponent } from 'app/entities/patient-favorite-doctor/patient-favorite-doctor-update.component';
import { PatientFavoriteDoctorService } from 'app/entities/patient-favorite-doctor/patient-favorite-doctor.service';
import { PatientFavoriteDoctor } from 'app/shared/model/patient-favorite-doctor.model';

describe('Component Tests', () => {
  describe('PatientFavoriteDoctor Management Update Component', () => {
    let comp: PatientFavoriteDoctorUpdateComponent;
    let fixture: ComponentFixture<PatientFavoriteDoctorUpdateComponent>;
    let service: PatientFavoriteDoctorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PatientFavoriteDoctorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PatientFavoriteDoctorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatientFavoriteDoctorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientFavoriteDoctorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PatientFavoriteDoctor(123);
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
        const entity = new PatientFavoriteDoctor();
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
