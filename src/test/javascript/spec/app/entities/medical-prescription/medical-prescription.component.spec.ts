import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YikondiTestModule } from '../../../test.module';
import { MedicalPrescriptionComponent } from 'app/entities/medical-prescription/medical-prescription.component';
import { MedicalPrescriptionService } from 'app/entities/medical-prescription/medical-prescription.service';
import { MedicalPrescription } from 'app/shared/model/medical-prescription.model';

describe('Component Tests', () => {
  describe('MedicalPrescription Management Component', () => {
    let comp: MedicalPrescriptionComponent;
    let fixture: ComponentFixture<MedicalPrescriptionComponent>;
    let service: MedicalPrescriptionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [MedicalPrescriptionComponent],
        providers: []
      })
        .overrideTemplate(MedicalPrescriptionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicalPrescriptionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalPrescriptionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MedicalPrescription(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.medicalPrescriptions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
