import { Routes } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { RegistartionPageComponent } from './components/registartion-page/registartion-page.component';
import { PasswordResetComponent } from './components/password-reset/password-reset.component';
import { CalendarComponent } from './components/calendar/calendar.component';
import { ProfilPageComponent } from './components/profil-page/profil-page.component';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomepageComponent },
  { path: 'login', component: LoginPageComponent },
  { path: 'registartion', component: RegistartionPageComponent },
  { path: 'passwordReset', component: PasswordResetComponent },
  { path: 'calendar', component: CalendarComponent },
  { path: 'profil', component: ProfilPageComponent },

];
