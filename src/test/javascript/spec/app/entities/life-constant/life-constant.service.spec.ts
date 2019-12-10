import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { LifeConstantService } from 'app/entities/life-constant/life-constant.service';
import { ILifeConstant, LifeConstant } from 'app/shared/model/life-constant.model';
import { LifeConstantName } from 'app/shared/model/enumerations/life-constant-name.model';
import { LifeConstantUnit } from 'app/shared/model/enumerations/life-constant-unit.model';

describe('Service Tests', () => {
  describe('LifeConstant Service', () => {
    let injector: TestBed;
    let service: LifeConstantService;
    let httpMock: HttpTestingController;
    let elemDefault: ILifeConstant;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(LifeConstantService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new LifeConstant(0, LifeConstantName.HEIGHT, LifeConstantUnit.NONE);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a LifeConstant', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new LifeConstant(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a LifeConstant', () => {
        const returnedFromService = Object.assign(
          {
            lifeConstantName: 'BBBBBB',
            lifeConstantUnit: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of LifeConstant', () => {
        const returnedFromService = Object.assign(
          {
            lifeConstantName: 'BBBBBB',
            lifeConstantUnit: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a LifeConstant', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
