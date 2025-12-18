import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Education } from '../models/education.model';
import { HttpClient } from '@angular/common/http';
import { FuelType } from '../models/fuel-type.model';

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

  getAllFuelType(): Observable<FuelType[]> {
    return this.http.get<FuelType[]>(`${this.baseUrl}/fuelType`)
  }

  getAllTown(): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/town`)
  }
}
