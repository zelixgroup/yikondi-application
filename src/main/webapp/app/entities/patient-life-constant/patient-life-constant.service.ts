import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPatientLifeConstant } from 'app/shared/model/patient-life-constant.model';

type EntityResponseType = HttpResponse<IPatientLifeConstant>;
type EntityArrayResponseType = HttpResponse<IPatientLifeConstant[]>;

@Injectable({ providedIn: 'root' })
export class PatientLifeConstantService {
  public resourceUrl = SERVER_API_URL + 'api/patient-life-constants';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/patient-life-constants';

  constructor(protected http: HttpClient) {}

  create(patientLifeConstant: IPatientLifeConstant): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patientLifeConstant);
    return this.http
      .post<IPatientLifeConstant>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(patientLifeConstant: IPatientLifeConstant): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patientLifeConstant);
    return this.http
      .put<IPatientLifeConstant>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPatientLifeConstant>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPatientLifeConstant[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPatientLifeConstant[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(patientLifeConstant: IPatientLifeConstant): IPatientLifeConstant {
    const copy: IPatientLifeConstant = Object.assign({}, patientLifeConstant, {
      measurementDatetime:
        patientLifeConstant.measurementDatetime != null && patientLifeConstant.measurementDatetime.isValid()
          ? patientLifeConstant.measurementDatetime.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.measurementDatetime = res.body.measurementDatetime != null ? moment(res.body.measurementDatetime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((patientLifeConstant: IPatientLifeConstant) => {
        patientLifeConstant.measurementDatetime =
          patientLifeConstant.measurementDatetime != null ? moment(patientLifeConstant.measurementDatetime) : null;
      });
    }
    return res;
  }
}
