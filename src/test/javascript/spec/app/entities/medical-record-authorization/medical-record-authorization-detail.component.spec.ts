import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { MedicalRecordAuthorizationDetailComponent } from 'app/entities/medical-record-authorization/medical-record-authorization-detail.component';
import { MedicalRecordAuthorization } from 'app/shared/model/medical-record-authorization.model';

describe('Component Tests', () => {
  describe('MedicalRecordAuthorization Management Detail Component', () => {
    let comp: MedicalRecordAuthorizationDetailComponent;
    let fixture: ComponentFixture<MedicalRecordAuthorizationDetailComponent>;
    const route = ({ data: of({ medicalRecordAuthorization: new MedicalRecordAuthorization(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [MedicalRecordAuthorizationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MedicalRecordAuthorizationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicalRecordAuthorizationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.medicalRecordAuthorization).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
