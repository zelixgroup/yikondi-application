import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDoctorAssistant } from 'app/shared/model/doctor-assistant.model';

type EntityResponseType = HttpResponse<IDoctorAssistant>;
type EntityArrayResponseType = HttpResponse<IDoctorAssistant[]>;

@Injectable({ providedIn: 'root' })
export class DoctorAssistantService {
  public resourceUrl = SERVER_API_URL + 'api/doctor-assistants';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/doctor-assistants';

  constructor(protected http: HttpClient) {}

  create(doctorAssistant: IDoctorAssistant): Observable<EntityResponseType> {
    return this.http.post<IDoctorAssistant>(this.resourceUrl, doctorAssistant, { observe: 'response' });
  }

  update(doctorAssistant: IDoctorAssistant): Observable<EntityResponseType> {
    return this.http.put<IDoctorAssistant>(this.resourceUrl, doctorAssistant, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDoctorAssistant>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDoctorAssistant[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDoctorAssistant[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
