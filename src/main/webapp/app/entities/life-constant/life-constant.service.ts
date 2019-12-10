import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILifeConstant } from 'app/shared/model/life-constant.model';

type EntityResponseType = HttpResponse<ILifeConstant>;
type EntityArrayResponseType = HttpResponse<ILifeConstant[]>;

@Injectable({ providedIn: 'root' })
export class LifeConstantService {
  public resourceUrl = SERVER_API_URL + 'api/life-constants';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/life-constants';

  constructor(protected http: HttpClient) {}

  create(lifeConstant: ILifeConstant): Observable<EntityResponseType> {
    return this.http.post<ILifeConstant>(this.resourceUrl, lifeConstant, { observe: 'response' });
  }

  update(lifeConstant: ILifeConstant): Observable<EntityResponseType> {
    return this.http.put<ILifeConstant>(this.resourceUrl, lifeConstant, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILifeConstant>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILifeConstant[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILifeConstant[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
