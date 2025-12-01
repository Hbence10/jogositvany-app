import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { User } from '../models/user.model';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/users';
  loggedUser =  signal<null | User>(null)

  constructor() { }

  login(email: string, password: string) : Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/login`, {email: email, password: password});
  }

  registration(user: User) : Observable<string> {
    console.log(user);
    return this.http.post<string>(`${this.baseUrl}/register`, user);
  }

  getVerificationCode(email: string) {
    return this.http.get(`${this.baseUrl}/getVerificationCode`, { params: new HttpParams().set("email", email) })
  }

  checkVerificationCode(userVCode: string): Observable<boolean> {
    return this.http.post<boolean>(`${this.baseUrl}/checkVerificationCode`, { vCode: userVCode })
  }

  passwordReset(email: string, newPassword: string, vCode: string) {
    return this.http.patch(`${this.baseUrl}/passwordReset`, { email: email, newPassword: newPassword, vCode: vCode })
  }
}
