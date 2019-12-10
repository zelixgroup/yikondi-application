import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPathology } from 'app/shared/model/pathology.model';

type EntityResponseType = HttpResponse<IPathology>;
type EntityArrayResponseType = HttpResponse<IPathology[]>;

@Injectable({ providedIn: 'root' })
export class PathologyService {
  public resourceUrl = SERVER_API_URL + 'api/pathologies';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/pathologies';

  constructor(protected http: HttpClient) {}

  create(pathology: IPathology): Observable<EntityResponseType> {
    return this.http.post<IPathology>(this.resourceUrl, pathology, { observe: 'response' });
  }

  update(pathology: IPathology): Observable<EntityResponseType> {
    return this.http.put<IPathology>(this.resourceUrl, pathology, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPathology>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPathology[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPathology[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
