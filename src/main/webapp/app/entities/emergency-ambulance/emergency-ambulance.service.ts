import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmergencyAmbulance } from 'app/shared/model/emergency-ambulance.model';

type EntityResponseType = HttpResponse<IEmergencyAmbulance>;
type EntityArrayResponseType = HttpResponse<IEmergencyAmbulance[]>;

@Injectable({ providedIn: 'root' })
export class EmergencyAmbulanceService {
  public resourceUrl = SERVER_API_URL + 'api/emergency-ambulances';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/emergency-ambulances';

  constructor(protected http: HttpClient) {}

  create(emergencyAmbulance: IEmergencyAmbulance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emergencyAmbulance);
    return this.http
      .post<IEmergencyAmbulance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(emergencyAmbulance: IEmergencyAmbulance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emergencyAmbulance);
    return this.http
      .put<IEmergencyAmbulance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmergencyAmbulance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmergencyAmbulance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmergencyAmbulance[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(emergencyAmbulance: IEmergencyAmbulance): IEmergencyAmbulance {
    const copy: IEmergencyAmbulance = Object.assign({}, emergencyAmbulance, {
      description:
        emergencyAmbulance.description != null && emergencyAmbulance.description.isValid()
          ? emergencyAmbulance.description.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.description = res.body.description != null ? moment(res.body.description) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((emergencyAmbulance: IEmergencyAmbulance) => {
        emergencyAmbulance.description = emergencyAmbulance.description != null ? moment(emergencyAmbulance.description) : null;
      });
    }
    return res;
  }
}
