import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInsuranceType } from 'app/shared/model/insurance-type.model';

type EntityResponseType = HttpResponse<IInsuranceType>;
type EntityArrayResponseType = HttpResponse<IInsuranceType[]>;

@Injectable({ providedIn: 'root' })
export class InsuranceTypeService {
  public resourceUrl = SERVER_API_URL + 'api/insurance-types';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/insurance-types';

  constructor(protected http: HttpClient) {}

  create(insuranceType: IInsuranceType): Observable<EntityResponseType> {
    return this.http.post<IInsuranceType>(this.resourceUrl, insuranceType, { observe: 'response' });
  }

  update(insuranceType: IInsuranceType): Observable<EntityResponseType> {
    return this.http.put<IInsuranceType>(this.resourceUrl, insuranceType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInsuranceType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInsuranceType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInsuranceType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
