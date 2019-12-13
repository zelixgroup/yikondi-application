import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDrug } from 'app/shared/model/drug.model';

type EntityResponseType = HttpResponse<IDrug>;
type EntityArrayResponseType = HttpResponse<IDrug[]>;

@Injectable({ providedIn: 'root' })
export class DrugService {
  public resourceUrl = SERVER_API_URL + 'api/drugs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/drugs';

  constructor(protected http: HttpClient) {}

  create(drug: IDrug): Observable<EntityResponseType> {
    return this.http.post<IDrug>(this.resourceUrl, drug, { observe: 'response' });
  }

  update(drug: IDrug): Observable<EntityResponseType> {
    return this.http.put<IDrug>(this.resourceUrl, drug, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDrug>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDrug[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDrug[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
