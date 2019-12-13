import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DrugDosageForm } from 'app/shared/model/drug-dosage-form.model';
import { DrugDosageFormService } from './drug-dosage-form.service';
import { DrugDosageFormComponent } from './drug-dosage-form.component';
import { DrugDosageFormDetailComponent } from './drug-dosage-form-detail.component';
import { DrugDosageFormUpdateComponent } from './drug-dosage-form-update.component';
import { IDrugDosageForm } from 'app/shared/model/drug-dosage-form.model';

@Injectable({ providedIn: 'root' })
export class DrugDosageFormResolve implements Resolve<IDrugDosageForm> {
  constructor(private service: DrugDosageFormService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDrugDosageForm> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((drugDosageForm: HttpResponse<DrugDosageForm>) => drugDosageForm.body));
    }
    return of(new DrugDosageForm());
  }
}

export const drugDosageFormRoute: Routes = [
  {
    path: '',
    component: DrugDosageFormComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.drugDosageForm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DrugDosageFormDetailComponent,
    resolve: {
      drugDosageForm: DrugDosageFormResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.drugDosageForm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DrugDosageFormUpdateComponent,
    resolve: {
      drugDosageForm: DrugDosageFormResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.drugDosageForm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DrugDosageFormUpdateComponent,
    resolve: {
      drugDosageForm: DrugDosageFormResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.drugDosageForm.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
