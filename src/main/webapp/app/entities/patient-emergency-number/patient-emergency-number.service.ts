import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPatientEmergencyNumber } from 'app/shared/model/patient-emergency-number.model';

type EntityResponseType = HttpResponse<IPatientEmergencyNumber>;
type EntityArrayResponseType = HttpResponse<IPatientEmergencyNumber[]>;

@Injectable({ providedIn: 'root' })
export class PatientEmergencyNumberService {
  public resourceUrl = SERVER_API_URL + 'api/patient-emergency-numbers';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/patient-emergency-numbers';

  constructor(protected http: HttpClient) {}

  create(patientEmergencyNumber: IPatientEmergencyNumber): Observable<EntityResponseType> {
    return this.http.post<IPatientEmergencyNumber>(this.resourceUrl, patientEmergencyNumber, { observe: 'response' });
  }

  update(patientEmergencyNumber: IPatientEmergencyNumber): Observable<EntityResponseType> {
    return this.http.put<IPatientEmergencyNumber>(this.resourceUrl, patientEmergencyNumber, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPatientEmergencyNumber>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPatientEmergencyNumber[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPatientEmergencyNumber[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
