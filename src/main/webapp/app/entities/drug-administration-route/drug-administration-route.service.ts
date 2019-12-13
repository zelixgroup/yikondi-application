import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDrugAdministrationRoute } from 'app/shared/model/drug-administration-route.model';

type EntityResponseType = HttpResponse<IDrugAdministrationRoute>;
type EntityArrayResponseType = HttpResponse<IDrugAdministrationRoute[]>;

@Injectable({ providedIn: 'root' })
export class DrugAdministrationRouteService {
  public resourceUrl = SERVER_API_URL + 'api/drug-administration-routes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/drug-administration-routes';

  constructor(protected http: HttpClient) {}

  create(drugAdministrationRoute: IDrugAdministrationRoute): Observable<EntityResponseType> {
    return this.http.post<IDrugAdministrationRoute>(this.resourceUrl, drugAdministrationRoute, { observe: 'response' });
  }

  update(drugAdministrationRoute: IDrugAdministrationRoute): Observable<EntityResponseType> {
    return this.http.put<IDrugAdministrationRoute>(this.resourceUrl, drugAdministrationRoute, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDrugAdministrationRoute>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDrugAdministrationRoute[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDrugAdministrationRoute[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
