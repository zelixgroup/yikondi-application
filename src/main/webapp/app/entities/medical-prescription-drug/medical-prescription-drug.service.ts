import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMedicalPrescriptionDrug } from 'app/shared/model/medical-prescription-drug.model';

type EntityResponseType = HttpResponse<IMedicalPrescriptionDrug>;
type EntityArrayResponseType = HttpResponse<IMedicalPrescriptionDrug[]>;

@Injectable({ providedIn: 'root' })
export class MedicalPrescriptionDrugService {
  public resourceUrl = SERVER_API_URL + 'api/medical-prescription-drugs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/medical-prescription-drugs';

  constructor(protected http: HttpClient) {}

  create(medicalPrescriptionDrug: IMedicalPrescriptionDrug): Observable<EntityResponseType> {
    return this.http.post<IMedicalPrescriptionDrug>(this.resourceUrl, medicalPrescriptionDrug, { observe: 'response' });
  }

  update(medicalPrescriptionDrug: IMedicalPrescriptionDrug): Observable<EntityResponseType> {
    return this.http.put<IMedicalPrescriptionDrug>(this.resourceUrl, medicalPrescriptionDrug, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMedicalPrescriptionDrug>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMedicalPrescriptionDrug[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMedicalPrescriptionDrug[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
