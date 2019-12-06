import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHoliday } from 'app/shared/model/holiday.model';

type EntityResponseType = HttpResponse<IHoliday>;
type EntityArrayResponseType = HttpResponse<IHoliday[]>;

@Injectable({ providedIn: 'root' })
export class HolidayService {
  public resourceUrl = SERVER_API_URL + 'api/holidays';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/holidays';

  constructor(protected http: HttpClient) {}

  create(holiday: IHoliday): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(holiday);
    return this.http
      .post<IHoliday>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(holiday: IHoliday): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(holiday);
    return this.http
      .put<IHoliday>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHoliday>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHoliday[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHoliday[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(holiday: IHoliday): IHoliday {
    const copy: IHoliday = Object.assign({}, holiday, {
      correspondingDate:
        holiday.correspondingDate != null && holiday.correspondingDate.isValid() ? holiday.correspondingDate.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.correspondingDate = res.body.correspondingDate != null ? moment(res.body.correspondingDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((holiday: IHoliday) => {
        holiday.correspondingDate = holiday.correspondingDate != null ? moment(holiday.correspondingDate) : null;
      });
    }
    return res;
  }
}
