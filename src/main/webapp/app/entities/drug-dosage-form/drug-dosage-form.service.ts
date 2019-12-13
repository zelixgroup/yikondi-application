import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDrugDosageForm } from 'app/shared/model/drug-dosage-form.model';

type EntityResponseType = HttpResponse<IDrugDosageForm>;
type EntityArrayResponseType = HttpResponse<IDrugDosageForm[]>;

@Injectable({ providedIn: 'root' })
export class DrugDosageFormService {
  public resourceUrl = SERVER_API_URL + 'api/drug-dosage-forms';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/drug-dosage-forms';

  constructor(protected http: HttpClient) {}

  create(drugDosageForm: IDrugDosageForm): Observable<EntityResponseType> {
    return this.http.post<IDrugDosageForm>(this.resourceUrl, drugDosageForm, { observe: 'response' });
  }

  update(drugDosageForm: IDrugDosageForm): Observable<EntityResponseType> {
    return this.http.put<IDrugDosageForm>(this.resourceUrl, drugDosageForm, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDrugDosageForm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDrugDosageForm[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDrugDosageForm[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
