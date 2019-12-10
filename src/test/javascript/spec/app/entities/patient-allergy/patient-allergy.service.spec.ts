import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PatientAllergyService } from 'app/entities/patient-allergy/patient-allergy.service';
import { IPatientAllergy, PatientAllergy } from 'app/shared/model/patient-allergy.model';

describe('Service Tests', () => {
  describe('PatientAllergy Service', () => {
    let injector: TestBed;
    let service: PatientAllergyService;
    let httpMock: HttpTestingController;
    let elemDefault: IPatientAllergy;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PatientAllergyService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PatientAllergy(0, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            observationDate: currentDate.format(DATE_FORMAT)
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

      it('should create a PatientAllergy', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            observationDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            observationDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new PatientAllergy(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a PatientAllergy', () => {
        const returnedFromService = Object.assign(
          {
            observationDate: currentDate.format(DATE_FORMAT),
            observations: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            observationDate: currentDate
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

      it('should return a list of PatientAllergy', () => {
        const returnedFromService = Object.assign(
          {
            observationDate: currentDate.format(DATE_FORMAT),
            observations: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            observationDate: currentDate
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

      it('should delete a PatientAllergy', () => {
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
