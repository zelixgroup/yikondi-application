import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISpeciality } from 'app/shared/model/speciality.model';

type EntityResponseType = HttpResponse<ISpeciality>;
type EntityArrayResponseType = HttpResponse<ISpeciality[]>;

@Injectable({ providedIn: 'root' })
export class SpecialityService {
  public resourceUrl = SERVER_API_URL + 'api/specialities';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/specialities';

  constructor(protected http: HttpClient) {}

  create(speciality: ISpeciality): Observable<EntityResponseType> {
    return this.http.post<ISpeciality>(this.resourceUrl, speciality, { observe: 'response' });
  }

  update(speciality: ISpeciality): Observable<EntityResponseType> {
    return this.http.put<ISpeciality>(this.resourceUrl, speciality, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISpeciality>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISpeciality[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISpeciality[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
