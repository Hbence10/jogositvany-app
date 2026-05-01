import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { User } from '../models/user.model';
import { Observable } from 'rxjs';
import { HomePageUser } from '../models/notEntity/homepageUser.model';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  private http = inject(HttpClient);
  private baseUrl = 'https://jogositvany-app.onrender.com/users';
  private router = inject(Router);
  private cookieService = inject(CookieService)
  loggedUser = signal<null | HomePageUser>(null)
  isRemember: boolean = false;

  constructor() { }

  login(email: string, password: string): Observable<HomePageUser> {
    return this.http.post<HomePageUser>(`${this.baseUrl}/login`, { email: email, password: password });
  }

  registration(user: User, registerAs: "student" | "instructor"): Observable<string> {
    return this.http.post<string>(`${this.baseUrl}/register/${registerAs}`, user);
  }

  getVerificationCode(email: string) {
    return this.http.get(`${this.baseUrl}/getVerificationCode`, { params: new HttpParams().set("email", email) })
  }

  checkVerificationCode(userVCode: string, email: string): Observable<boolean> {
    return this.http.post<boolean>(`${this.baseUrl}/checkVerificationCode`, { vCode: userVCode, email: email })
  }

  passwordReset(email: string, newPassword: string, vCode: string) {
    return this.http.patch(`${this.baseUrl}/passwordReset`, { email: email, newPassword: newPassword, vCode: vCode })
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/${id}`)
  }

  updateUser(userId: number, firstName: string, lastName: string, email: string, phone: string, birthDateText: string, gender: string, educationId: number): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/${userId}`, {
      firstName: firstName,
      lastName: lastName,
      email: email,
      phone: phone,
      birthDate: birthDateText,
      gender: gender,
      educationId: educationId
    })
  }

  changePfp(userId: number, formData: FormData): Observable<User> {
    return this.http.patch<User>(`${this.baseUrl}/pfp/${userId}`, formData)
  }

  deleteUser(userId: number) {
    return this.http.delete(`${this.baseUrl}/${userId}`)
  }

  getAllUser(pageNumber: number = 0): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${this.baseUrl}?page=${pageNumber}&size=10`, { observe: "response" })
  }

  getUserAfterReload(id: number): Observable<HomePageUser> {
    return this.http.get<HomePageUser>(`${this.baseUrl}/${id}?isLogin=true`)
  }

  getAllUserWithoutPaginator(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}`)
  }

  logout() {
    this.cookieService.deleteAll()
    sessionStorage.clear()
    localStorage.clear()
    this.loggedUser.set(null)
    this.router.navigate(['/login']);
  }
}
