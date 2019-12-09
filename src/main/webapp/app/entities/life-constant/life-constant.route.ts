import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { LifeConstant } from 'app/shared/model/life-constant.model';
import { LifeConstantService } from './life-constant.service';
import { LifeConstantComponent } from './life-constant.component';
import { LifeConstantDetailComponent } from './life-constant-detail.component';
import { LifeConstantUpdateComponent } from './life-constant-update.component';
import { ILifeConstant } from 'app/shared/model/life-constant.model';

@Injectable({ providedIn: 'root' })
export class LifeConstantResolve implements Resolve<ILifeConstant> {
  constructor(private service: LifeConstantService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILifeConstant> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((lifeConstant: HttpResponse<LifeConstant>) => lifeConstant.body));
    }
    return of(new LifeConstant());
  }
}

export const lifeConstantRoute: Routes = [
  {
    path: '',
    component: LifeConstantComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.lifeConstant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LifeConstantDetailComponent,
    resolve: {
      lifeConstant: LifeConstantResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.lifeConstant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LifeConstantUpdateComponent,
    resolve: {
      lifeConstant: LifeConstantResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.lifeConstant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LifeConstantUpdateComponent,
    resolve: {
      lifeConstant: LifeConstantResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.lifeConstant.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
