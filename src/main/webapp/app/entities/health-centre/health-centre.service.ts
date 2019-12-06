import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHealthCentre } from 'app/shared/model/health-centre.model';

type EntityResponseType = HttpResponse<IHealthCentre>;
type EntityArrayResponseType = HttpResponse<IHealthCentre[]>;

@Injectable({ providedIn: 'root' })
export class HealthCentreService {
  public resourceUrl = SERVER_API_URL + 'api/health-centres';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/health-centres';

  constructor(protected http: HttpClient) {}

  create(healthCentre: IHealthCentre): Observable<EntityResponseType> {
    return this.http.post<IHealthCentre>(this.resourceUrl, healthCentre, { observe: 'response' });
  }

  update(healthCentre: IHealthCentre): Observable<EntityResponseType> {
    return this.http.put<IHealthCentre>(this.resourceUrl, healthCentre, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHealthCentre>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHealthCentre[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHealthCentre[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
