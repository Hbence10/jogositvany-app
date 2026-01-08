import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Education } from '../models/education.model';
import { HttpClient } from '@angular/common/http';
import { FuelType } from '../models/fuel-type.model';
import { PaymentMethod } from '../models/payment-method.model';
import { Status } from '../models/status.model';
import { VehicleType } from '../models/vehicle-type.model';

@Injectable({
  providedIn: 'root'
})
export class OtherStuffServiceService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080'

  constructor() { }

  getAllEducation(): Observable<Education[]>{
    return this.http.get<Education[]>(`${this.baseUrl}/education`)
  }

  getAllFuelType(): Observable<FuelType[]> {
    return this.http.get<FuelType[]>(`${this.baseUrl}/fuelType`)
  }

  getAllTown(): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/town`)
  }

  getAllVehicleType(): Observable<VehicleType[]> {
    return this.http.get<VehicleType[]>(`${this.baseUrl}/vehicleType`)
  }

  getAllPaymentMethod(): Observable<PaymentMethod[]> {
    return this.http.get<PaymentMethod[]>(`${this.baseUrl}/paymentMethod`)
  }

  getAllStatus(): Observable<Status[]> {
    return this.http.get<Status[]>(`${this.baseUrl}/status`)
  }
}
