import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Allergy } from 'app/shared/model/allergy.model';
import { AllergyService } from './allergy.service';
import { AllergyComponent } from './allergy.component';
import { AllergyDetailComponent } from './allergy-detail.component';
import { AllergyUpdateComponent } from './allergy-update.component';
import { IAllergy } from 'app/shared/model/allergy.model';

@Injectable({ providedIn: 'root' })
export class AllergyResolve implements Resolve<IAllergy> {
  constructor(private service: AllergyService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAllergy> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((allergy: HttpResponse<Allergy>) => allergy.body));
    }
    return of(new Allergy());
  }
}

export const allergyRoute: Routes = [
  {
    path: '',
    component: AllergyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.allergy.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AllergyDetailComponent,
    resolve: {
      allergy: AllergyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.allergy.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AllergyUpdateComponent,
    resolve: {
      allergy: AllergyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.allergy.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AllergyUpdateComponent,
    resolve: {
      allergy: AllergyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.allergy.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
