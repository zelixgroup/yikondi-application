import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { DoctorWorkingSlotService } from 'app/entities/doctor-working-slot/doctor-working-slot.service';
import { IDoctorWorkingSlot, DoctorWorkingSlot } from 'app/shared/model/doctor-working-slot.model';
import { DayOfTheWeek } from 'app/shared/model/enumerations/day-of-the-week.model';

describe('Service Tests', () => {
  describe('DoctorWorkingSlot Service', () => {
    let injector: TestBed;
    let service: DoctorWorkingSlotService;
    let httpMock: HttpTestingController;
    let elemDefault: IDoctorWorkingSlot;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DoctorWorkingSlotService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DoctorWorkingSlot(0, DayOfTheWeek.MONDAY, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
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

      it('should create a DoctorWorkingSlot', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new DoctorWorkingSlot(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a DoctorWorkingSlot', () => {
        const returnedFromService = Object.assign(
          {
            dayOfTheWeek: 'BBBBBB',
            startTime: 'BBBBBB',
            endTime: 'BBBBBB',
            description: 'BBBBBB'
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

      it('should return a list of DoctorWorkingSlot', () => {
        const returnedFromService = Object.assign(
          {
            dayOfTheWeek: 'BBBBBB',
            startTime: 'BBBBBB',
            endTime: 'BBBBBB',
            description: 'BBBBBB'
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

      it('should delete a DoctorWorkingSlot', () => {
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
