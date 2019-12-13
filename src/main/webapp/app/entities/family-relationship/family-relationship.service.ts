import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFamilyRelationship } from 'app/shared/model/family-relationship.model';

type EntityResponseType = HttpResponse<IFamilyRelationship>;
type EntityArrayResponseType = HttpResponse<IFamilyRelationship[]>;

@Injectable({ providedIn: 'root' })
export class FamilyRelationshipService {
  public resourceUrl = SERVER_API_URL + 'api/family-relationships';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/family-relationships';

  constructor(protected http: HttpClient) {}

  create(familyRelationship: IFamilyRelationship): Observable<EntityResponseType> {
    return this.http.post<IFamilyRelationship>(this.resourceUrl, familyRelationship, { observe: 'response' });
  }

  update(familyRelationship: IFamilyRelationship): Observable<EntityResponseType> {
    return this.http.put<IFamilyRelationship>(this.resourceUrl, familyRelationship, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFamilyRelationship>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFamilyRelationship[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFamilyRelationship[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
