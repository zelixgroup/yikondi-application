import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMedicalPrescriptionAnalysis } from 'app/shared/model/medical-prescription-analysis.model';

type EntityResponseType = HttpResponse<IMedicalPrescriptionAnalysis>;
type EntityArrayResponseType = HttpResponse<IMedicalPrescriptionAnalysis[]>;

@Injectable({ providedIn: 'root' })
export class MedicalPrescriptionAnalysisService {
  public resourceUrl = SERVER_API_URL + 'api/medical-prescription-analyses';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/medical-prescription-analyses';

  constructor(protected http: HttpClient) {}

  create(medicalPrescriptionAnalysis: IMedicalPrescriptionAnalysis): Observable<EntityResponseType> {
    return this.http.post<IMedicalPrescriptionAnalysis>(this.resourceUrl, medicalPrescriptionAnalysis, { observe: 'response' });
  }

  update(medicalPrescriptionAnalysis: IMedicalPrescriptionAnalysis): Observable<EntityResponseType> {
    return this.http.put<IMedicalPrescriptionAnalysis>(this.resourceUrl, medicalPrescriptionAnalysis, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMedicalPrescriptionAnalysis>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMedicalPrescriptionAnalysis[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMedicalPrescriptionAnalysis[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
