import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPharmacyAllNightPlanning } from 'app/shared/model/pharmacy-all-night-planning.model';

type EntityResponseType = HttpResponse<IPharmacyAllNightPlanning>;
type EntityArrayResponseType = HttpResponse<IPharmacyAllNightPlanning[]>;

@Injectable({ providedIn: 'root' })
export class PharmacyAllNightPlanningService {
  public resourceUrl = SERVER_API_URL + 'api/pharmacy-all-night-plannings';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/pharmacy-all-night-plannings';

  constructor(protected http: HttpClient) {}

  create(pharmacyAllNightPlanning: IPharmacyAllNightPlanning): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pharmacyAllNightPlanning);
    return this.http
      .post<IPharmacyAllNightPlanning>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pharmacyAllNightPlanning: IPharmacyAllNightPlanning): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pharmacyAllNightPlanning);
    return this.http
      .put<IPharmacyAllNightPlanning>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPharmacyAllNightPlanning>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPharmacyAllNightPlanning[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPharmacyAllNightPlanning[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(pharmacyAllNightPlanning: IPharmacyAllNightPlanning): IPharmacyAllNightPlanning {
    const copy: IPharmacyAllNightPlanning = Object.assign({}, pharmacyAllNightPlanning, {
      plannedStartDate:
        pharmacyAllNightPlanning.plannedStartDate != null && pharmacyAllNightPlanning.plannedStartDate.isValid()
          ? pharmacyAllNightPlanning.plannedStartDate.format(DATE_FORMAT)
          : null,
      plannedEndDate:
        pharmacyAllNightPlanning.plannedEndDate != null && pharmacyAllNightPlanning.plannedEndDate.isValid()
          ? pharmacyAllNightPlanning.plannedEndDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.plannedStartDate = res.body.plannedStartDate != null ? moment(res.body.plannedStartDate) : null;
      res.body.plannedEndDate = res.body.plannedEndDate != null ? moment(res.body.plannedEndDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pharmacyAllNightPlanning: IPharmacyAllNightPlanning) => {
        pharmacyAllNightPlanning.plannedStartDate =
          pharmacyAllNightPlanning.plannedStartDate != null ? moment(pharmacyAllNightPlanning.plannedStartDate) : null;
        pharmacyAllNightPlanning.plannedEndDate =
          pharmacyAllNightPlanning.plannedEndDate != null ? moment(pharmacyAllNightPlanning.plannedEndDate) : null;
      });
    }
    return res;
  }
}
