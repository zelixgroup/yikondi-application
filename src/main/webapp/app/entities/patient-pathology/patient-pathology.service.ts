import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPatientPathology } from 'app/shared/model/patient-pathology.model';

type EntityResponseType = HttpResponse<IPatientPathology>;
type EntityArrayResponseType = HttpResponse<IPatientPathology[]>;

@Injectable({ providedIn: 'root' })
export class PatientPathologyService {
  public resourceUrl = SERVER_API_URL + 'api/patient-pathologies';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/patient-pathologies';

  constructor(protected http: HttpClient) {}

  create(patientPathology: IPatientPathology): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patientPathology);
    return this.http
      .post<IPatientPathology>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(patientPathology: IPatientPathology): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patientPathology);
    return this.http
      .put<IPatientPathology>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPatientPathology>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPatientPathology[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPatientPathology[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(patientPathology: IPatientPathology): IPatientPathology {
    const copy: IPatientPathology = Object.assign({}, patientPathology, {
      observationDate:
        patientPathology.observationDate != null && patientPathology.observationDate.isValid()
          ? patientPathology.observationDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.observationDate = res.body.observationDate != null ? moment(res.body.observationDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((patientPathology: IPatientPathology) => {
        patientPathology.observationDate = patientPathology.observationDate != null ? moment(patientPathology.observationDate) : null;
      });
    }
    return res;
  }
}
