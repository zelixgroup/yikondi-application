import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILifeConstantUnit } from 'app/shared/model/life-constant-unit.model';

type EntityResponseType = HttpResponse<ILifeConstantUnit>;
type EntityArrayResponseType = HttpResponse<ILifeConstantUnit[]>;

@Injectable({ providedIn: 'root' })
export class LifeConstantUnitService {
  public resourceUrl = SERVER_API_URL + 'api/life-constant-units';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/life-constant-units';

  constructor(protected http: HttpClient) {}

  create(lifeConstantUnit: ILifeConstantUnit): Observable<EntityResponseType> {
    return this.http.post<ILifeConstantUnit>(this.resourceUrl, lifeConstantUnit, { observe: 'response' });
  }

  update(lifeConstantUnit: ILifeConstantUnit): Observable<EntityResponseType> {
    return this.http.put<ILifeConstantUnit>(this.resourceUrl, lifeConstantUnit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILifeConstantUnit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILifeConstantUnit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILifeConstantUnit[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
