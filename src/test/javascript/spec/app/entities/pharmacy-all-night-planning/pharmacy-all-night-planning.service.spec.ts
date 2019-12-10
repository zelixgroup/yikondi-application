import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PharmacyAllNightPlanningService } from 'app/entities/pharmacy-all-night-planning/pharmacy-all-night-planning.service';
import { IPharmacyAllNightPlanning, PharmacyAllNightPlanning } from 'app/shared/model/pharmacy-all-night-planning.model';

describe('Service Tests', () => {
  describe('PharmacyAllNightPlanning Service', () => {
    let injector: TestBed;
    let service: PharmacyAllNightPlanningService;
    let httpMock: HttpTestingController;
    let elemDefault: IPharmacyAllNightPlanning;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PharmacyAllNightPlanningService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PharmacyAllNightPlanning(0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            plannedStartDate: currentDate.format(DATE_FORMAT),
            plannedEndDate: currentDate.format(DATE_FORMAT)
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

      it('should create a PharmacyAllNightPlanning', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            plannedStartDate: currentDate.format(DATE_FORMAT),
            plannedEndDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            plannedStartDate: currentDate,
            plannedEndDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new PharmacyAllNightPlanning(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a PharmacyAllNightPlanning', () => {
        const returnedFromService = Object.assign(
          {
            plannedStartDate: currentDate.format(DATE_FORMAT),
            plannedEndDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            plannedStartDate: currentDate,
            plannedEndDate: currentDate
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

      it('should return a list of PharmacyAllNightPlanning', () => {
        const returnedFromService = Object.assign(
          {
            plannedStartDate: currentDate.format(DATE_FORMAT),
            plannedEndDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            plannedStartDate: currentDate,
            plannedEndDate: currentDate
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

      it('should delete a PharmacyAllNightPlanning', () => {
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
