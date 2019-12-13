import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMedicalPrescription } from 'app/shared/model/medical-prescription.model';

type EntityResponseType = HttpResponse<IMedicalPrescription>;
type EntityArrayResponseType = HttpResponse<IMedicalPrescription[]>;

@Injectable({ providedIn: 'root' })
export class MedicalPrescriptionService {
  public resourceUrl = SERVER_API_URL + 'api/medical-prescriptions';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/medical-prescriptions';

  constructor(protected http: HttpClient) {}

  create(medicalPrescription: IMedicalPrescription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalPrescription);
    return this.http
      .post<IMedicalPrescription>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(medicalPrescription: IMedicalPrescription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalPrescription);
    return this.http
      .put<IMedicalPrescription>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMedicalPrescription>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMedicalPrescription[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMedicalPrescription[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(medicalPrescription: IMedicalPrescription): IMedicalPrescription {
    const copy: IMedicalPrescription = Object.assign({}, medicalPrescription, {
      prescriptionDateTime:
        medicalPrescription.prescriptionDateTime != null && medicalPrescription.prescriptionDateTime.isValid()
          ? medicalPrescription.prescriptionDateTime.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.prescriptionDateTime = res.body.prescriptionDateTime != null ? moment(res.body.prescriptionDateTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((medicalPrescription: IMedicalPrescription) => {
        medicalPrescription.prescriptionDateTime =
          medicalPrescription.prescriptionDateTime != null ? moment(medicalPrescription.prescriptionDateTime) : null;
      });
    }
    return res;
  }
}
