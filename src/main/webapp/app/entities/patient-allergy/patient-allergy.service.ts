import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPatientAllergy } from 'app/shared/model/patient-allergy.model';

type EntityResponseType = HttpResponse<IPatientAllergy>;
type EntityArrayResponseType = HttpResponse<IPatientAllergy[]>;

@Injectable({ providedIn: 'root' })
export class PatientAllergyService {
  public resourceUrl = SERVER_API_URL + 'api/patient-allergies';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/patient-allergies';

  constructor(protected http: HttpClient) {}

  create(patientAllergy: IPatientAllergy): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patientAllergy);
    return this.http
      .post<IPatientAllergy>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(patientAllergy: IPatientAllergy): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patientAllergy);
    return this.http
      .put<IPatientAllergy>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPatientAllergy>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPatientAllergy[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPatientAllergy[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(patientAllergy: IPatientAllergy): IPatientAllergy {
    const copy: IPatientAllergy = Object.assign({}, patientAllergy, {
      observationDate:
        patientAllergy.observationDate != null && patientAllergy.observationDate.isValid()
          ? patientAllergy.observationDate.format(DATE_FORMAT)
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
      res.body.forEach((patientAllergy: IPatientAllergy) => {
        patientAllergy.observationDate = patientAllergy.observationDate != null ? moment(patientAllergy.observationDate) : null;
      });
    }
    return res;
  }
}
