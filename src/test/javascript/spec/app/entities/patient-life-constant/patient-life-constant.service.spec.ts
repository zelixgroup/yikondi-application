import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PatientLifeConstantService } from 'app/entities/patient-life-constant/patient-life-constant.service';
import { IPatientLifeConstant, PatientLifeConstant } from 'app/shared/model/patient-life-constant.model';

describe('Service Tests', () => {
  describe('PatientLifeConstant Service', () => {
    let injector: TestBed;
    let service: PatientLifeConstantService;
    let httpMock: HttpTestingController;
    let elemDefault: IPatientLifeConstant;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PatientLifeConstantService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PatientLifeConstant(0, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            measurementDatetime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a PatientLifeConstant', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            measurementDatetime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            measurementDatetime: currentDate
          },
          returnedFromService
        );
        service
          .create(new PatientLifeConstant(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a PatientLifeConstant', () => {
        const returnedFromService = Object.assign(
          {
            measurementDatetime: currentDate.format(DATE_TIME_FORMAT),
            measuredValue: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            measurementDatetime: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of PatientLifeConstant', () => {
        const returnedFromService = Object.assign(
          {
            measurementDatetime: currentDate.format(DATE_TIME_FORMAT),
            measuredValue: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            measurementDatetime: currentDate
          },
          returnedFromService
        );
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

      it('should delete a PatientLifeConstant', () => {
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
