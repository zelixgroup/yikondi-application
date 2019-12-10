import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Pathology } from 'app/shared/model/pathology.model';
import { PathologyService } from './pathology.service';
import { PathologyComponent } from './pathology.component';
import { PathologyDetailComponent } from './pathology-detail.component';
import { PathologyUpdateComponent } from './pathology-update.component';
import { IPathology } from 'app/shared/model/pathology.model';

@Injectable({ providedIn: 'root' })
export class PathologyResolve implements Resolve<IPathology> {
  constructor(private service: PathologyService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPathology> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((pathology: HttpResponse<Pathology>) => pathology.body));
    }
    return of(new Pathology());
  }
}

export const pathologyRoute: Routes = [
  {
    path: '',
    component: PathologyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.pathology.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PathologyDetailComponent,
    resolve: {
      pathology: PathologyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.pathology.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PathologyUpdateComponent,
    resolve: {
      pathology: PathologyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.pathology.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PathologyUpdateComponent,
    resolve: {
      pathology: PathologyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.pathology.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
