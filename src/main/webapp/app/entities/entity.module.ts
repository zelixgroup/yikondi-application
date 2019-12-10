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
      },
      {
        path: 'emergency-ambulance',
        loadChildren: () => import('./emergency-ambulance/emergency-ambulance.module').then(m => m.YikondiEmergencyAmbulanceModule)
      },
      {
        path: 'life-constant',
        loadChildren: () => import('./life-constant/life-constant.module').then(m => m.YikondiLifeConstantModule)
      },
      {
        path: 'patient-life-constant',
        loadChildren: () => import('./patient-life-constant/patient-life-constant.module').then(m => m.YikondiPatientLifeConstantModule)
      },
      {
        path: 'insurance',
        loadChildren: () => import('./insurance/insurance.module').then(m => m.YikondiInsuranceModule)
      },
      {
        path: 'patient-insurance-coverage',
        loadChildren: () =>
          import('./patient-insurance-coverage/patient-insurance-coverage.module').then(m => m.YikondiPatientInsuranceCoverageModule)
      },
      {
        path: 'allergy',
        loadChildren: () => import('./allergy/allergy.module').then(m => m.YikondiAllergyModule)
      },
      {
        path: 'patient-allergy',
        loadChildren: () => import('./patient-allergy/patient-allergy.module').then(m => m.YikondiPatientAllergyModule)
      },
      {
        path: 'pathology',
        loadChildren: () => import('./pathology/pathology.module').then(m => m.YikondiPathologyModule)
      },
      {
        path: 'patient-pathology',
        loadChildren: () => import('./patient-pathology/patient-pathology.module').then(m => m.YikondiPatientPathologyModule)
      },
      {
        path: 'patient-emergency-number',
        loadChildren: () =>
          import('./patient-emergency-number/patient-emergency-number.module').then(m => m.YikondiPatientEmergencyNumberModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class YikondiEntityModule {}
