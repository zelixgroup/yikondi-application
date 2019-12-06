import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDoctorSchedule } from 'app/shared/model/doctor-schedule.model';

type EntityResponseType = HttpResponse<IDoctorSchedule>;
type EntityArrayResponseType = HttpResponse<IDoctorSchedule[]>;

@Injectable({ providedIn: 'root' })
export class DoctorScheduleService {
  public resourceUrl = SERVER_API_URL + 'api/doctor-schedules';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/doctor-schedules';

  constructor(protected http: HttpClient) {}

  create(doctorSchedule: IDoctorSchedule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(doctorSchedule);
    return this.http
      .post<IDoctorSchedule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(doctorSchedule: IDoctorSchedule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(doctorSchedule);
    return this.http
      .put<IDoctorSchedule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDoctorSchedule>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDoctorSchedule[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDoctorSchedule[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(doctorSchedule: IDoctorSchedule): IDoctorSchedule {
    const copy: IDoctorSchedule = Object.assign({}, doctorSchedule, {
      scheduleStartDate:
        doctorSchedule.scheduleStartDate != null && doctorSchedule.scheduleStartDate.isValid()
          ? doctorSchedule.scheduleStartDate.toJSON()
          : null,
      scheduleEndDate:
        doctorSchedule.scheduleEndDate != null && doctorSchedule.scheduleEndDate.isValid() ? doctorSchedule.scheduleEndDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.scheduleStartDate = res.body.scheduleStartDate != null ? moment(res.body.scheduleStartDate) : null;
      res.body.scheduleEndDate = res.body.scheduleEndDate != null ? moment(res.body.scheduleEndDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((doctorSchedule: IDoctorSchedule) => {
        doctorSchedule.scheduleStartDate = doctorSchedule.scheduleStartDate != null ? moment(doctorSchedule.scheduleStartDate) : null;
        doctorSchedule.scheduleEndDate = doctorSchedule.scheduleEndDate != null ? moment(doctorSchedule.scheduleEndDate) : null;
      });
    }
    return res;
  }
}
