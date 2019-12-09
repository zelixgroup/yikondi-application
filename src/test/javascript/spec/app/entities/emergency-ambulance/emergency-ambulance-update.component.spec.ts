import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { EmergencyAmbulanceUpdateComponent } from 'app/entities/emergency-ambulance/emergency-ambulance-update.component';
import { EmergencyAmbulanceService } from 'app/entities/emergency-ambulance/emergency-ambulance.service';
import { EmergencyAmbulance } from 'app/shared/model/emergency-ambulance.model';

describe('Component Tests', () => {
  describe('EmergencyAmbulance Management Update Component', () => {
    let comp: EmergencyAmbulanceUpdateComponent;
    let fixture: ComponentFixture<EmergencyAmbulanceUpdateComponent>;
    let service: EmergencyAmbulanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [EmergencyAmbulanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EmergencyAmbulanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmergencyAmbulanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmergencyAmbulanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmergencyAmbulance(123);
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
        const entity = new EmergencyAmbulance();
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
