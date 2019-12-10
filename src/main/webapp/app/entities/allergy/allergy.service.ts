import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAllergy } from 'app/shared/model/allergy.model';

type EntityResponseType = HttpResponse<IAllergy>;
type EntityArrayResponseType = HttpResponse<IAllergy[]>;

@Injectable({ providedIn: 'root' })
export class AllergyService {
  public resourceUrl = SERVER_API_URL + 'api/allergies';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/allergies';

  constructor(protected http: HttpClient) {}

  create(allergy: IAllergy): Observable<EntityResponseType> {
    return this.http.post<IAllergy>(this.resourceUrl, allergy, { observe: 'response' });
  }

  update(allergy: IAllergy): Observable<EntityResponseType> {
    return this.http.put<IAllergy>(this.resourceUrl, allergy, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAllergy>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAllergy[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAllergy[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
