import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPatientAppointement } from 'app/shared/model/patient-appointement.model';

type EntityResponseType = HttpResponse<IPatientAppointement>;
type EntityArrayResponseType = HttpResponse<IPatientAppointement[]>;

@Injectable({ providedIn: 'root' })
export class PatientAppointementService {
  public resourceUrl = SERVER_API_URL + 'api/patient-appointements';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/patient-appointements';

  constructor(protected http: HttpClient) {}

  create(patientAppointement: IPatientAppointement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patientAppointement);
    return this.http
      .post<IPatientAppointement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(patientAppointement: IPatientAppointement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patientAppointement);
    return this.http
      .put<IPatientAppointement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPatientAppointement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPatientAppointement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPatientAppointement[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(patientAppointement: IPatientAppointement): IPatientAppointement {
    const copy: IPatientAppointement = Object.assign({}, patientAppointement, {
      appointementDateTime:
        patientAppointement.appointementDateTime != null && patientAppointement.appointementDateTime.isValid()
          ? patientAppointement.appointementDateTime.toJSON()
          : null,
      appointementMakingDateTime:
        patientAppointement.appointementMakingDateTime != null && patientAppointement.appointementMakingDateTime.isValid()
          ? patientAppointement.appointementMakingDateTime.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.appointementDateTime = res.body.appointementDateTime != null ? moment(res.body.appointementDateTime) : null;
      res.body.appointementMakingDateTime =
        res.body.appointementMakingDateTime != null ? moment(res.body.appointementMakingDateTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((patientAppointement: IPatientAppointement) => {
        patientAppointement.appointementDateTime =
          patientAppointement.appointementDateTime != null ? moment(patientAppointement.appointementDateTime) : null;
        patientAppointement.appointementMakingDateTime =
          patientAppointement.appointementMakingDateTime != null ? moment(patientAppointement.appointementMakingDateTime) : null;
      });
    }
    return res;
  }
}
