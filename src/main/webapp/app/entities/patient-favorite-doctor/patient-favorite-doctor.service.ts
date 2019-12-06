import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPatientFavoriteDoctor } from 'app/shared/model/patient-favorite-doctor.model';

type EntityResponseType = HttpResponse<IPatientFavoriteDoctor>;
type EntityArrayResponseType = HttpResponse<IPatientFavoriteDoctor[]>;

@Injectable({ providedIn: 'root' })
export class PatientFavoriteDoctorService {
  public resourceUrl = SERVER_API_URL + 'api/patient-favorite-doctors';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/patient-favorite-doctors';

  constructor(protected http: HttpClient) {}

  create(patientFavoriteDoctor: IPatientFavoriteDoctor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patientFavoriteDoctor);
    return this.http
      .post<IPatientFavoriteDoctor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(patientFavoriteDoctor: IPatientFavoriteDoctor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patientFavoriteDoctor);
    return this.http
      .put<IPatientFavoriteDoctor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPatientFavoriteDoctor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPatientFavoriteDoctor[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPatientFavoriteDoctor[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(patientFavoriteDoctor: IPatientFavoriteDoctor): IPatientFavoriteDoctor {
    const copy: IPatientFavoriteDoctor = Object.assign({}, patientFavoriteDoctor, {
      activationDate:
        patientFavoriteDoctor.activationDate != null && patientFavoriteDoctor.activationDate.isValid()
          ? patientFavoriteDoctor.activationDate.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.activationDate = res.body.activationDate != null ? moment(res.body.activationDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((patientFavoriteDoctor: IPatientFavoriteDoctor) => {
        patientFavoriteDoctor.activationDate =
          patientFavoriteDoctor.activationDate != null ? moment(patientFavoriteDoctor.activationDate) : null;
      });
    }
    return res;
  }
}
