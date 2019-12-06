import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { City } from 'app/shared/model/city.model';
import { CityService } from './city.service';
import { CityComponent } from './city.component';
import { CityDetailComponent } from './city-detail.component';
import { CityUpdateComponent } from './city-update.component';
import { ICity } from 'app/shared/model/city.model';

@Injectable({ providedIn: 'root' })
export class CityResolve implements Resolve<ICity> {
  constructor(private service: CityService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICity> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((city: HttpResponse<City>) => city.body));
    }
    return of(new City());
  }
}

export const cityRoute: Routes = [
  {
    path: '',
    component: CityComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.city.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CityDetailComponent,
    resolve: {
      city: CityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.city.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CityUpdateComponent,
    resolve: {
      city: CityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.city.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CityUpdateComponent,
    resolve: {
      city: CityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.city.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
