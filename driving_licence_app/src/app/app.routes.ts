import { Routes } from '@angular/router';
import { AboutComponent } from './components/about/about.component';
import { FaqComponent } from './components/faq/faq.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { PasswordResetComponent } from './components/password-reset/password-reset.component';
import { RegistrationPageComponent } from './components/registration-page/registration-page.component';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';
import { AuthGuard } from './routerGuards/AuthGuard';
import { RoleGuard } from './routerGuards/RoleGuard';
import { ForSchoolsComponent } from './components/for-schools/for-schools.component';
import { ForInstructorsComponent } from './components/for-instructors/for-instructors.component';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomepageComponent },
  { path: 'login', component: LoginPageComponent },
  { path: 'registration', component: RegistrationPageComponent },
  { path: 'about', component: AboutComponent },
  { path: 'faq', component: FaqComponent },
  { path: 'unauthorized', component: UnauthorizedComponent },
  { path: 'password-reset', component: PasswordResetComponent },
  { path: 'forSchools', component: ForSchoolsComponent },
  { path: 'forInstructors', component: ForInstructorsComponent },

  {
    path: 'calendar',
    loadComponent: () =>
      import('./components/calendar/calendar.component').then(
        (c) => c.CalendarComponent,
      ),
    canMatch: [AuthGuard, RoleGuard],
    data: { roles: ['ROLE_student', 'ROLE_instructor'] },
  },
  {
    path: 'searchPage/:type',
    loadComponent: () =>
      import('./components/search-page/search-page.component').then(
        (c) => c.SearchPageComponent,
      ),
    canMatch: [AuthGuard],
  },
  {
    path: 'profil/:type/:id',
    loadComponent: () =>
      import('./components/profil-page/profil-page.component').then(
        (c) => c.ProfilPageComponent,
      ),
    canMatch: [AuthGuard],
  },
  {
    path: 'request/:owner',
    loadComponent: () =>
      import('./components/request-list/request-list.component').then(
        (c) => c.RequestListComponent,
      ),
    canMatch: [AuthGuard, RoleGuard],
    data: {
      roles: ['ROLE_instructor', 'ROLE_school_admin', 'ROLE_school_owner'],
    },
  },
  {
    path: 'users/:userType',
    loadComponent: () =>
      import('./components/user-list/user-list.component').then(
        (c) => c.UserListComponent,
      ),
    canMatch: [AuthGuard, RoleGuard],
    data: {
      roles: [
        'ROLE_instructor',
        'ROLE_school_admin',
        'ROLE_school_owner',
        'ROLE_administrator',
      ],
    },
  },
  { path: '**', component: PageNotFoundComponent },
];
