import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHealthCentreDoctor } from 'app/shared/model/health-centre-doctor.model';

type EntityResponseType = HttpResponse<IHealthCentreDoctor>;
type EntityArrayResponseType = HttpResponse<IHealthCentreDoctor[]>;

@Injectable({ providedIn: 'root' })
export class HealthCentreDoctorService {
  public resourceUrl = SERVER_API_URL + 'api/health-centre-doctors';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/health-centre-doctors';

  constructor(protected http: HttpClient) {}

  create(healthCentreDoctor: IHealthCentreDoctor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(healthCentreDoctor);
    return this.http
      .post<IHealthCentreDoctor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(healthCentreDoctor: IHealthCentreDoctor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(healthCentreDoctor);
    return this.http
      .put<IHealthCentreDoctor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHealthCentreDoctor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHealthCentreDoctor[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHealthCentreDoctor[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(healthCentreDoctor: IHealthCentreDoctor): IHealthCentreDoctor {
    const copy: IHealthCentreDoctor = Object.assign({}, healthCentreDoctor, {
      startDate:
        healthCentreDoctor.startDate != null && healthCentreDoctor.startDate.isValid()
          ? healthCentreDoctor.startDate.format(DATE_FORMAT)
          : null,
      endDate:
        healthCentreDoctor.endDate != null && healthCentreDoctor.endDate.isValid() ? healthCentreDoctor.endDate.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
      res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((healthCentreDoctor: IHealthCentreDoctor) => {
        healthCentreDoctor.startDate = healthCentreDoctor.startDate != null ? moment(healthCentreDoctor.startDate) : null;
        healthCentreDoctor.endDate = healthCentreDoctor.endDate != null ? moment(healthCentreDoctor.endDate) : null;
      });
    }
    return res;
  }
}
