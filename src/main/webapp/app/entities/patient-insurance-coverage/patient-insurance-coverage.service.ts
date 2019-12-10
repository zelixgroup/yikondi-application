import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPatientInsuranceCoverage } from 'app/shared/model/patient-insurance-coverage.model';

type EntityResponseType = HttpResponse<IPatientInsuranceCoverage>;
type EntityArrayResponseType = HttpResponse<IPatientInsuranceCoverage[]>;

@Injectable({ providedIn: 'root' })
export class PatientInsuranceCoverageService {
  public resourceUrl = SERVER_API_URL + 'api/patient-insurance-coverages';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/patient-insurance-coverages';

  constructor(protected http: HttpClient) {}

  create(patientInsuranceCoverage: IPatientInsuranceCoverage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patientInsuranceCoverage);
    return this.http
      .post<IPatientInsuranceCoverage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(patientInsuranceCoverage: IPatientInsuranceCoverage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patientInsuranceCoverage);
    return this.http
      .put<IPatientInsuranceCoverage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPatientInsuranceCoverage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPatientInsuranceCoverage[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPatientInsuranceCoverage[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(patientInsuranceCoverage: IPatientInsuranceCoverage): IPatientInsuranceCoverage {
    const copy: IPatientInsuranceCoverage = Object.assign({}, patientInsuranceCoverage, {
      startDate:
        patientInsuranceCoverage.startDate != null && patientInsuranceCoverage.startDate.isValid()
          ? patientInsuranceCoverage.startDate.format(DATE_FORMAT)
          : null,
      endDate:
        patientInsuranceCoverage.endDate != null && patientInsuranceCoverage.endDate.isValid()
          ? patientInsuranceCoverage.endDate.format(DATE_FORMAT)
          : null
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
      res.body.forEach((patientInsuranceCoverage: IPatientInsuranceCoverage) => {
        patientInsuranceCoverage.startDate = patientInsuranceCoverage.startDate != null ? moment(patientInsuranceCoverage.startDate) : null;
        patientInsuranceCoverage.endDate = patientInsuranceCoverage.endDate != null ? moment(patientInsuranceCoverage.endDate) : null;
      });
    }
    return res;
  }
}
