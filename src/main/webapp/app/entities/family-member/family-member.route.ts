import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { FamilyMember } from 'app/shared/model/family-member.model';
import { FamilyMemberService } from './family-member.service';
import { FamilyMemberComponent } from './family-member.component';
import { FamilyMemberDetailComponent } from './family-member-detail.component';
import { FamilyMemberUpdateComponent } from './family-member-update.component';
import { IFamilyMember } from 'app/shared/model/family-member.model';

@Injectable({ providedIn: 'root' })
export class FamilyMemberResolve implements Resolve<IFamilyMember> {
  constructor(private service: FamilyMemberService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFamilyMember> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((familyMember: HttpResponse<FamilyMember>) => familyMember.body));
    }
    return of(new FamilyMember());
  }
}

export const familyMemberRoute: Routes = [
  {
    path: '',
    component: FamilyMemberComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.familyMember.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FamilyMemberDetailComponent,
    resolve: {
      familyMember: FamilyMemberResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.familyMember.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FamilyMemberUpdateComponent,
    resolve: {
      familyMember: FamilyMemberResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.familyMember.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FamilyMemberUpdateComponent,
    resolve: {
      familyMember: FamilyMemberResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.familyMember.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
