import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { MedicalPrescriptionDrugDetailComponent } from 'app/entities/medical-prescription-drug/medical-prescription-drug-detail.component';
import { MedicalPrescriptionDrug } from 'app/shared/model/medical-prescription-drug.model';

describe('Component Tests', () => {
  describe('MedicalPrescriptionDrug Management Detail Component', () => {
    let comp: MedicalPrescriptionDrugDetailComponent;
    let fixture: ComponentFixture<MedicalPrescriptionDrugDetailComponent>;
    const route = ({ data: of({ medicalPrescriptionDrug: new MedicalPrescriptionDrug(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [MedicalPrescriptionDrugDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MedicalPrescriptionDrugDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicalPrescriptionDrugDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.medicalPrescriptionDrug).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
