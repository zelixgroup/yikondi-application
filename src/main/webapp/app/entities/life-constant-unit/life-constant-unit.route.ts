import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { LifeConstantUnit } from 'app/shared/model/life-constant-unit.model';
import { LifeConstantUnitService } from './life-constant-unit.service';
import { LifeConstantUnitComponent } from './life-constant-unit.component';
import { LifeConstantUnitDetailComponent } from './life-constant-unit-detail.component';
import { LifeConstantUnitUpdateComponent } from './life-constant-unit-update.component';
import { ILifeConstantUnit } from 'app/shared/model/life-constant-unit.model';

@Injectable({ providedIn: 'root' })
export class LifeConstantUnitResolve implements Resolve<ILifeConstantUnit> {
  constructor(private service: LifeConstantUnitService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILifeConstantUnit> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((lifeConstantUnit: HttpResponse<LifeConstantUnit>) => lifeConstantUnit.body));
    }
    return of(new LifeConstantUnit());
  }
}

export const lifeConstantUnitRoute: Routes = [
  {
    path: '',
    component: LifeConstantUnitComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.lifeConstantUnit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LifeConstantUnitDetailComponent,
    resolve: {
      lifeConstantUnit: LifeConstantUnitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.lifeConstantUnit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LifeConstantUnitUpdateComponent,
    resolve: {
      lifeConstantUnit: LifeConstantUnitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.lifeConstantUnit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LifeConstantUnitUpdateComponent,
    resolve: {
      lifeConstantUnit: LifeConstantUnitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.lifeConstantUnit.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
