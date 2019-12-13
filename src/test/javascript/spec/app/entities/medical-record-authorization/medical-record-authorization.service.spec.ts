import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MedicalRecordAuthorizationService } from 'app/entities/medical-record-authorization/medical-record-authorization.service';
import { IMedicalRecordAuthorization, MedicalRecordAuthorization } from 'app/shared/model/medical-record-authorization.model';

describe('Service Tests', () => {
  describe('MedicalRecordAuthorization Service', () => {
    let injector: TestBed;
    let service: MedicalRecordAuthorizationService;
    let httpMock: HttpTestingController;
    let elemDefault: IMedicalRecordAuthorization;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(MedicalRecordAuthorizationService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MedicalRecordAuthorization(0, currentDate, currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            authorizationDateTime: currentDate.format(DATE_TIME_FORMAT),
            authorizationStartDateTime: currentDate.format(DATE_TIME_FORMAT),
            authorizationEndDateTime: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a MedicalRecordAuthorization', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            authorizationDateTime: currentDate.format(DATE_TIME_FORMAT),
            authorizationStartDateTime: currentDate.format(DATE_TIME_FORMAT),
            authorizationEndDateTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            authorizationDateTime: currentDate,
            authorizationStartDateTime: currentDate,
            authorizationEndDateTime: currentDate
          },
          returnedFromService
        );
        service
          .create(new MedicalRecordAuthorization(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a MedicalRecordAuthorization', () => {
        const returnedFromService = Object.assign(
          {
            authorizationDateTime: currentDate.format(DATE_TIME_FORMAT),
            authorizationStartDateTime: currentDate.format(DATE_TIME_FORMAT),
            authorizationEndDateTime: currentDate.format(DATE_TIME_FORMAT),
            observations: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            authorizationDateTime: currentDate,
            authorizationStartDateTime: currentDate,
            authorizationEndDateTime: currentDate
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

      it('should return a list of MedicalRecordAuthorization', () => {
        const returnedFromService = Object.assign(
          {
            authorizationDateTime: currentDate.format(DATE_TIME_FORMAT),
            authorizationStartDateTime: currentDate.format(DATE_TIME_FORMAT),
            authorizationEndDateTime: currentDate.format(DATE_TIME_FORMAT),
            observations: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            authorizationDateTime: currentDate,
            authorizationStartDateTime: currentDate,
            authorizationEndDateTime: currentDate
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

      it('should delete a MedicalRecordAuthorization', () => {
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
