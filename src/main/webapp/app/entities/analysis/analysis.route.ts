import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Analysis } from 'app/shared/model/analysis.model';
import { AnalysisService } from './analysis.service';
import { AnalysisComponent } from './analysis.component';
import { AnalysisDetailComponent } from './analysis-detail.component';
import { AnalysisUpdateComponent } from './analysis-update.component';
import { IAnalysis } from 'app/shared/model/analysis.model';

@Injectable({ providedIn: 'root' })
export class AnalysisResolve implements Resolve<IAnalysis> {
  constructor(private service: AnalysisService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnalysis> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((analysis: HttpResponse<Analysis>) => analysis.body));
    }
    return of(new Analysis());
  }
}

export const analysisRoute: Routes = [
  {
    path: '',
    component: AnalysisComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.analysis.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnalysisDetailComponent,
    resolve: {
      analysis: AnalysisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.analysis.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnalysisUpdateComponent,
    resolve: {
      analysis: AnalysisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.analysis.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnalysisUpdateComponent,
    resolve: {
      analysis: AnalysisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.analysis.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
