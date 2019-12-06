import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDoctorWorkingSlot } from 'app/shared/model/doctor-working-slot.model';

type EntityResponseType = HttpResponse<IDoctorWorkingSlot>;
type EntityArrayResponseType = HttpResponse<IDoctorWorkingSlot[]>;

@Injectable({ providedIn: 'root' })
export class DoctorWorkingSlotService {
  public resourceUrl = SERVER_API_URL + 'api/doctor-working-slots';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/doctor-working-slots';

  constructor(protected http: HttpClient) {}

  create(doctorWorkingSlot: IDoctorWorkingSlot): Observable<EntityResponseType> {
    return this.http.post<IDoctorWorkingSlot>(this.resourceUrl, doctorWorkingSlot, { observe: 'response' });
  }

  update(doctorWorkingSlot: IDoctorWorkingSlot): Observable<EntityResponseType> {
    return this.http.put<IDoctorWorkingSlot>(this.resourceUrl, doctorWorkingSlot, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDoctorWorkingSlot>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDoctorWorkingSlot[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDoctorWorkingSlot[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
