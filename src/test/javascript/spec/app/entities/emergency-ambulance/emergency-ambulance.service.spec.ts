import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EmergencyAmbulanceService } from 'app/entities/emergency-ambulance/emergency-ambulance.service';
import { IEmergencyAmbulance, EmergencyAmbulance } from 'app/shared/model/emergency-ambulance.model';

describe('Service Tests', () => {
  describe('EmergencyAmbulance Service', () => {
    let injector: TestBed;
    let service: EmergencyAmbulanceService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmergencyAmbulance;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(EmergencyAmbulanceService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new EmergencyAmbulance(0, 'AAAAAAA', currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            description: currentDate.format(DATE_FORMAT)
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

      it('should create a EmergencyAmbulance', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            description: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            description: currentDate
          },
          returnedFromService
        );
        service
          .create(new EmergencyAmbulance(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a EmergencyAmbulance', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            description: currentDate.format(DATE_FORMAT),
            phoneNumber: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            description: currentDate
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

      it('should return a list of EmergencyAmbulance', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            description: currentDate.format(DATE_FORMAT),
            phoneNumber: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            description: currentDate
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

      it('should delete a EmergencyAmbulance', () => {
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
