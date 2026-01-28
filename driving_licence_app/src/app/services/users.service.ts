import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { User } from '../models/user.model';
import { Observable } from 'rxjs';
import { HomePageUser } from '../models/notEntity/homepageUser.model';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/users';
  private router = inject(Router);
  loggedUser =  signal<null | HomePageUser>(null)

  constructor() { }

  login(email: string, password: string) : Observable<HomePageUser> {
    return this.http.post<HomePageUser>(`${this.baseUrl}/login`, {email: email, password: password});
  }

  logout(){
    this.loggedUser.set(null);
    this.router.navigate(['/login']);
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

  getUserById(id: number): Observable<User>{
    return this.http.get<User>(`${this.baseUrl}/${id}`)
  }

  updateUser(userId: number, firstName: string, lastName:string, email: string, phone: string, birthDateText: string, gender: string, educationId: number): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/update/${userId}`, {
      firstName: firstName,
      lastName: lastName,
      email: email,
      phone: phone,
      birthDateText: birthDateText,
      gender: gender,
      educationId: educationId
    })
  }

  changePfp(userId: number, formData: FormData): Observable<User> {
      return this.http.patch<User>(`${this.baseUrl}/pfp/${userId}`, formData)
  }

  deleteUser(userId: number) {
    return this.http.delete(`${this.baseUrl}/delete/${userId}`)
  }

  getAllUser(): Observable<{id:number, name: string, imagePath: string, userId: number}[]> {
    return this.http.get<{id:number, name: string, imagePath: string, userId: number}[]>(`${this.baseUrl}?page=0&size=10`)
  }
}
