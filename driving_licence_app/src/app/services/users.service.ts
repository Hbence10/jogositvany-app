import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private http = inject(HttpClient);

  constructor() { }

  login(email: string, password: string) : Observable<User> {
    return this.http.get<User>(`http://localhost:8080/users/login?email=${email}&password=${password}`);
  }
}
