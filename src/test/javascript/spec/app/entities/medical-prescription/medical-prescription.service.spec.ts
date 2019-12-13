import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MedicalPrescriptionService } from 'app/entities/medical-prescription/medical-prescription.service';
import { IMedicalPrescription, MedicalPrescription } from 'app/shared/model/medical-prescription.model';

describe('Service Tests', () => {
  describe('MedicalPrescription Service', () => {
    let injector: TestBed;
    let service: MedicalPrescriptionService;
    let httpMock: HttpTestingController;
    let elemDefault: IMedicalPrescription;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(MedicalPrescriptionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MedicalPrescription(0, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            prescriptionDateTime: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a MedicalPrescription', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            prescriptionDateTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            prescriptionDateTime: currentDate
          },
          returnedFromService
        );
        service
          .create(new MedicalPrescription(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a MedicalPrescription', () => {
        const returnedFromService = Object.assign(
          {
            prescriptionDateTime: currentDate.format(DATE_TIME_FORMAT),
            observations: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            prescriptionDateTime: currentDate
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

      it('should return a list of MedicalPrescription', () => {
        const returnedFromService = Object.assign(
          {
            prescriptionDateTime: currentDate.format(DATE_TIME_FORMAT),
            observations: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            prescriptionDateTime: currentDate
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

      it('should delete a MedicalPrescription', () => {
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
