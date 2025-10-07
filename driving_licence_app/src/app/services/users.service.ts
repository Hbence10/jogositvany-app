import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { User } from '../models/user.model';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/';
  loggedUser =  signal<null | User>(null)

  constructor() { }

  login(email: string, password: string) : Observable<User> {
    return this.http.post<User>(`${this.baseUrl}users/login`, {email: email, password: password});
  }

  registration(user: User) : Observable<string> {

    console.log(user);
    return this.http.post<string>(`${this.baseUrl}users/register`, user);
  }
}
