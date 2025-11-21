import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Education } from '../models/education.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class OtherStuffServiceService {
  private http = inject(HttpClient);


  constructor() { }

  getAllEducation(): Observable<Education[]>{
    this.http.get<Education[]>(``)
  }
}
