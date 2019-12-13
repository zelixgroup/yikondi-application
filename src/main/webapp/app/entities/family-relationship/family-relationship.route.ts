import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { FamilyRelationship } from 'app/shared/model/family-relationship.model';
import { FamilyRelationshipService } from './family-relationship.service';
import { FamilyRelationshipComponent } from './family-relationship.component';
import { FamilyRelationshipDetailComponent } from './family-relationship-detail.component';
import { FamilyRelationshipUpdateComponent } from './family-relationship-update.component';
import { IFamilyRelationship } from 'app/shared/model/family-relationship.model';

@Injectable({ providedIn: 'root' })
export class FamilyRelationshipResolve implements Resolve<IFamilyRelationship> {
  constructor(private service: FamilyRelationshipService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFamilyRelationship> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((familyRelationship: HttpResponse<FamilyRelationship>) => familyRelationship.body));
    }
    return of(new FamilyRelationship());
  }
}

export const familyRelationshipRoute: Routes = [
  {
    path: '',
    component: FamilyRelationshipComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.familyRelationship.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FamilyRelationshipDetailComponent,
    resolve: {
      familyRelationship: FamilyRelationshipResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.familyRelationship.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FamilyRelationshipUpdateComponent,
    resolve: {
      familyRelationship: FamilyRelationshipResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.familyRelationship.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FamilyRelationshipUpdateComponent,
    resolve: {
      familyRelationship: FamilyRelationshipResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.familyRelationship.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
