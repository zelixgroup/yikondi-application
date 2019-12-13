import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MedicalPrescriptionAnalysis } from 'app/shared/model/medical-prescription-analysis.model';
import { MedicalPrescriptionAnalysisService } from './medical-prescription-analysis.service';
import { MedicalPrescriptionAnalysisComponent } from './medical-prescription-analysis.component';
import { MedicalPrescriptionAnalysisDetailComponent } from './medical-prescription-analysis-detail.component';
import { MedicalPrescriptionAnalysisUpdateComponent } from './medical-prescription-analysis-update.component';
import { IMedicalPrescriptionAnalysis } from 'app/shared/model/medical-prescription-analysis.model';

@Injectable({ providedIn: 'root' })
export class MedicalPrescriptionAnalysisResolve implements Resolve<IMedicalPrescriptionAnalysis> {
  constructor(private service: MedicalPrescriptionAnalysisService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMedicalPrescriptionAnalysis> {
    const id = route.params['id'];
    if (id) {
      return this.service
        .find(id)
        .pipe(map((medicalPrescriptionAnalysis: HttpResponse<MedicalPrescriptionAnalysis>) => medicalPrescriptionAnalysis.body));
    }
    return of(new MedicalPrescriptionAnalysis());
  }
}

export const medicalPrescriptionAnalysisRoute: Routes = [
  {
    path: '',
    component: MedicalPrescriptionAnalysisComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.medicalPrescriptionAnalysis.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MedicalPrescriptionAnalysisDetailComponent,
    resolve: {
      medicalPrescriptionAnalysis: MedicalPrescriptionAnalysisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.medicalPrescriptionAnalysis.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MedicalPrescriptionAnalysisUpdateComponent,
    resolve: {
      medicalPrescriptionAnalysis: MedicalPrescriptionAnalysisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.medicalPrescriptionAnalysis.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MedicalPrescriptionAnalysisUpdateComponent,
    resolve: {
      medicalPrescriptionAnalysis: MedicalPrescriptionAnalysisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.medicalPrescriptionAnalysis.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
