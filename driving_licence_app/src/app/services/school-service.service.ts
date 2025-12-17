import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { School } from '../models/school.model';

@Injectable({
  providedIn: 'root'
})
export class SchoolServiceService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/school';

  constructor() { }

  getSchoolById(id: number): Observable<School>{
    return this.http.get<School>(`${this.baseUrl}/${id}`)
  }
}
