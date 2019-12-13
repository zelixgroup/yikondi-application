import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnalysis } from 'app/shared/model/analysis.model';

type EntityResponseType = HttpResponse<IAnalysis>;
type EntityArrayResponseType = HttpResponse<IAnalysis[]>;

@Injectable({ providedIn: 'root' })
export class AnalysisService {
  public resourceUrl = SERVER_API_URL + 'api/analyses';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/analyses';

  constructor(protected http: HttpClient) {}

  create(analysis: IAnalysis): Observable<EntityResponseType> {
    return this.http.post<IAnalysis>(this.resourceUrl, analysis, { observe: 'response' });
  }

  update(analysis: IAnalysis): Observable<EntityResponseType> {
    return this.http.put<IAnalysis>(this.resourceUrl, analysis, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnalysis>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnalysis[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnalysis[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
