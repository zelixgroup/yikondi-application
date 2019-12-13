import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { MedicalPrescriptionDetailComponent } from 'app/entities/medical-prescription/medical-prescription-detail.component';
import { MedicalPrescription } from 'app/shared/model/medical-prescription.model';

describe('Component Tests', () => {
  describe('MedicalPrescription Management Detail Component', () => {
    let comp: MedicalPrescriptionDetailComponent;
    let fixture: ComponentFixture<MedicalPrescriptionDetailComponent>;
    const route = ({ data: of({ medicalPrescription: new MedicalPrescription(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [MedicalPrescriptionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MedicalPrescriptionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicalPrescriptionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.medicalPrescription).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
