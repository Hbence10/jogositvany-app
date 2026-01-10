import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DrivingLessonRequest } from '../models/driving-lesson-request.model';
import { InstructorJoinRequest } from '../models/instructor-join-request.model';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root',
})
export class InstructorServiceService {

  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/instructor';

  constructor() {}

  handleJoinRequest(requestId: number, status: 'accept' | 'refuse') {
    return this.http.post('', {});
  }

  handleDrivingLessonRequest(requestId: number, status: 'accept' | 'refuse') {
    return this.http.post('', {});
  }

  getAllJoinRequestByInstructor(
    id: number
  ): Observable<InstructorJoinRequest[]> {
    return this.http.get<InstructorJoinRequest[]>('');
  }

  getDrivingLessonRequestByInstructor(
    id: number
  ): Observable<DrivingLessonRequest[]> {
    return this.http.get<DrivingLessonRequest[]>('');
  }

  updateInstructor(instructorId: number, promoText: string, vehicleId: number, vehicleName: string, licensePlate: string, fuelTypeId: number, vehicleTypeId: number): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/update/${instructorId}`, {
      promoText: promoText,
      vehicleId: vehicleId,
      vehicleName: vehicleName,
      licensePlate: licensePlate,
      fuelTypeId: fuelTypeId,
      vehicleTypeId: vehicleTypeId
    })
  }
}
