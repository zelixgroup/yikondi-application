import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YikondiTestModule } from '../../../test.module';
import { PathologyDetailComponent } from 'app/entities/pathology/pathology-detail.component';
import { Pathology } from 'app/shared/model/pathology.model';

describe('Component Tests', () => {
  describe('Pathology Management Detail Component', () => {
    let comp: PathologyDetailComponent;
    let fixture: ComponentFixture<PathologyDetailComponent>;
    const route = ({ data: of({ pathology: new Pathology(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YikondiTestModule],
        declarations: [PathologyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PathologyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PathologyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pathology).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
