import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHealthCentreCategory } from 'app/shared/model/health-centre-category.model';

type EntityResponseType = HttpResponse<IHealthCentreCategory>;
type EntityArrayResponseType = HttpResponse<IHealthCentreCategory[]>;

@Injectable({ providedIn: 'root' })
export class HealthCentreCategoryService {
  public resourceUrl = SERVER_API_URL + 'api/health-centre-categories';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/health-centre-categories';

  constructor(protected http: HttpClient) {}

  create(healthCentreCategory: IHealthCentreCategory): Observable<EntityResponseType> {
    return this.http.post<IHealthCentreCategory>(this.resourceUrl, healthCentreCategory, { observe: 'response' });
  }

  update(healthCentreCategory: IHealthCentreCategory): Observable<EntityResponseType> {
    return this.http.put<IHealthCentreCategory>(this.resourceUrl, healthCentreCategory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHealthCentreCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHealthCentreCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHealthCentreCategory[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
