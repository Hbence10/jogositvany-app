import { Routes } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { RegistrationPageComponent } from './components/registration-page/registration-page.component';
import { PasswordResetComponent } from './components/password-reset/password-reset.component';
import { CalendarComponent } from './components/calendar/calendar.component';
import { ProfilPageComponent } from './components/profil-page/profil-page.component';
import { AboutComponent } from './components/about/about.component';
import { FaqComponent } from './components/faq/faq.component';
import { ForSchoolsComponent } from './components/for-schools/for-schools.component';
import { ForInstructorsComponent } from './components/for-instructors/for-instructors.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { SchoolRegistrationComponent } from './components/school-registration/school-registration.component';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';
import { SearchPageComponent } from './components/search-page/search-page.component';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomepageComponent },
  { path: 'login', component: LoginPageComponent },
  { path: 'registration', component: RegistrationPageComponent },
  { path: 'passwordReset', component: PasswordResetComponent },
  { path: 'calendar', component: CalendarComponent },
  { path: 'profil', component: ProfilPageComponent },
  { path: 'about', component: AboutComponent },
  { path: 'faq', component: FaqComponent },
  { path: 'forSchools', component: ForSchoolsComponent },
  { path: 'forInstructors', component: ForInstructorsComponent },
  { path: 'notFound', component: NotFoundComponent },
  { path: 'schoolRegistration', component: SchoolRegistrationComponent },
  { path: 'unauthorized', component: UnauthorizedComponent },
  { path: 'searchPage', component: SearchPageComponent },
  { path: 'registration', component: RegistrationPageComponent },
  { path: 'password-reset', component: PasswordResetComponent },

];
