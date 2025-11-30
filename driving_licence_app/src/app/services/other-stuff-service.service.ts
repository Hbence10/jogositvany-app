import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Education } from '../models/education.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class OtherStuffServiceService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080'

  constructor() { }

  getAllEducation(): Observable<Education[]>{
    return this.http.get<Education[]>(`http://localhost:8080/education`)
  }
}
