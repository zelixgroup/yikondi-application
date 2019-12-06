import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'holiday',
        loadChildren: () => import('./holiday/holiday.module').then(m => m.YikondiHolidayModule)
      },
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.YikondiCountryModule)
      },
      {
        path: 'city',
        loadChildren: () => import('./city/city.module').then(m => m.YikondiCityModule)
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.YikondiAddressModule)
      },
      {
        path: 'health-centre-category',
        loadChildren: () => import('./health-centre-category/health-centre-category.module').then(m => m.YikondiHealthCentreCategoryModule)
      },
      {
        path: 'health-centre',
        loadChildren: () => import('./health-centre/health-centre.module').then(m => m.YikondiHealthCentreModule)
      },
      {
        path: 'pharmacy',
        loadChildren: () => import('./pharmacy/pharmacy.module').then(m => m.YikondiPharmacyModule)
      },
      {
        path: 'pharmacy-all-night-planning',
        loadChildren: () =>
          import('./pharmacy-all-night-planning/pharmacy-all-night-planning.module').then(m => m.YikondiPharmacyAllNightPlanningModule)
      },
      {
        path: 'speciality',
        loadChildren: () => import('./speciality/speciality.module').then(m => m.YikondiSpecialityModule)
      },
      {
        path: 'doctor',
        loadChildren: () => import('./doctor/doctor.module').then(m => m.YikondiDoctorModule)
      },
      {
        path: 'patient',
        loadChildren: () => import('./patient/patient.module').then(m => m.YikondiPatientModule)
      },
      {
        path: 'health-centre-doctor',
        loadChildren: () => import('./health-centre-doctor/health-centre-doctor.module').then(m => m.YikondiHealthCentreDoctorModule)
      },
      {
        path: 'doctor-schedule',
        loadChildren: () => import('./doctor-schedule/doctor-schedule.module').then(m => m.YikondiDoctorScheduleModule)
      },
      {
        path: 'doctor-working-slot',
        loadChildren: () => import('./doctor-working-slot/doctor-working-slot.module').then(m => m.YikondiDoctorWorkingSlotModule)
      },
      {
        path: 'patient-appointement',
        loadChildren: () => import('./patient-appointement/patient-appointement.module').then(m => m.YikondiPatientAppointementModule)
      },
      {
        path: 'patient-favorite-pharmacy',
        loadChildren: () =>
          import('./patient-favorite-pharmacy/patient-favorite-pharmacy.module').then(m => m.YikondiPatientFavoritePharmacyModule)
      },
      {
        path: 'patient-favorite-doctor',
        loadChildren: () =>
          import('./patient-favorite-doctor/patient-favorite-doctor.module').then(m => m.YikondiPatientFavoriteDoctorModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class YikondiEntityModule {}
