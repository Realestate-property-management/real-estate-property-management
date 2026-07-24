import { Routes } from '@angular/router';

import { Landing } from './pages/landing/landing';
import { Login } from './pages/login/login';
import { Signup } from './pages/signup/signup';

import { MainLayout } from './layouts/main-layout/main-layout';
import { Dashboard } from './pages/dashboard/dashboard';
import { Properties } from './pages/properties/properties';
import { Tenants } from './pages/tenants/tenants';
import { Leases } from './pages/leases/leases';
import { Compliance } from './pages/compliance/compliance';
import { Maintenance } from './pages/maintenance/maintenance';
import { Inspections } from './pages/inspections/inspections';
import { Notifications } from './pages/notifications/notifications';
import { Profile } from './pages/profile/profile';
import { Contractors } from './pages/contractors/contractors';
import { Calendar } from './pages/calendar/calendar';
import { NotFound } from './pages/not-found/not-found';

export const routes: Routes = [

  {
    path: '',
    component: Landing
  },

  {
    path: 'login',
    component: Login
  },

  {
    path: 'signup',
    component: Signup
  },

  {
    path: 'dashboard',
    component: MainLayout,

   children: [
  {
    path: '',
    component: Dashboard
  },
  {
    path: 'properties',
    component: Properties
  },
  {
    path: 'tenants',
    component: Tenants
  },
  {
    path: 'leases',
    component: Leases
  },
  {
    path: 'compliance',
    component: Compliance
  },
  {
    path: 'maintenance',
    component: Maintenance
  },
  {
    path: 'inspections',
    component: Inspections
  },

  {
  path: 'contractors',
  component: Contractors
},
{
  path: 'calendar',
  component: Calendar
},

 {
  path: 'notifications',
  component: Notifications
},
{
  path: 'profile',
  component: Profile
}
]
  },

  {
    path: '**',
    redirectTo: ''
  }

];