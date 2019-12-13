import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMedicalRecordAuthorization } from 'app/shared/model/medical-record-authorization.model';

type EntityResponseType = HttpResponse<IMedicalRecordAuthorization>;
type EntityArrayResponseType = HttpResponse<IMedicalRecordAuthorization[]>;

@Injectable({ providedIn: 'root' })
export class MedicalRecordAuthorizationService {
  public resourceUrl = SERVER_API_URL + 'api/medical-record-authorizations';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/medical-record-authorizations';

  constructor(protected http: HttpClient) {}

  create(medicalRecordAuthorization: IMedicalRecordAuthorization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalRecordAuthorization);
    return this.http
      .post<IMedicalRecordAuthorization>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(medicalRecordAuthorization: IMedicalRecordAuthorization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalRecordAuthorization);
    return this.http
      .put<IMedicalRecordAuthorization>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMedicalRecordAuthorization>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMedicalRecordAuthorization[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMedicalRecordAuthorization[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(medicalRecordAuthorization: IMedicalRecordAuthorization): IMedicalRecordAuthorization {
    const copy: IMedicalRecordAuthorization = Object.assign({}, medicalRecordAuthorization, {
      authorizationDateTime:
        medicalRecordAuthorization.authorizationDateTime != null && medicalRecordAuthorization.authorizationDateTime.isValid()
          ? medicalRecordAuthorization.authorizationDateTime.toJSON()
          : null,
      authorizationStartDateTime:
        medicalRecordAuthorization.authorizationStartDateTime != null && medicalRecordAuthorization.authorizationStartDateTime.isValid()
          ? medicalRecordAuthorization.authorizationStartDateTime.toJSON()
          : null,
      authorizationEndDateTime:
        medicalRecordAuthorization.authorizationEndDateTime != null && medicalRecordAuthorization.authorizationEndDateTime.isValid()
          ? medicalRecordAuthorization.authorizationEndDateTime.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.authorizationDateTime = res.body.authorizationDateTime != null ? moment(res.body.authorizationDateTime) : null;
      res.body.authorizationStartDateTime =
        res.body.authorizationStartDateTime != null ? moment(res.body.authorizationStartDateTime) : null;
      res.body.authorizationEndDateTime = res.body.authorizationEndDateTime != null ? moment(res.body.authorizationEndDateTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((medicalRecordAuthorization: IMedicalRecordAuthorization) => {
        medicalRecordAuthorization.authorizationDateTime =
          medicalRecordAuthorization.authorizationDateTime != null ? moment(medicalRecordAuthorization.authorizationDateTime) : null;
        medicalRecordAuthorization.authorizationStartDateTime =
          medicalRecordAuthorization.authorizationStartDateTime != null
            ? moment(medicalRecordAuthorization.authorizationStartDateTime)
            : null;
        medicalRecordAuthorization.authorizationEndDateTime =
          medicalRecordAuthorization.authorizationEndDateTime != null ? moment(medicalRecordAuthorization.authorizationEndDateTime) : null;
      });
    }
    return res;
  }
}
