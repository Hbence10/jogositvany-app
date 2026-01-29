import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DrivingLessonRequest } from '../models/driving-lesson-request.model';
import { InstructorJoinRequest } from '../models/instructor-join-request.model';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';
import { Instructors } from '../models/instructors.model';

@Injectable({
  providedIn: 'root',
})
export class InstructorServiceService {

  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/instructor';

  constructor() {}

  handleJoinRequest(requestId: number, status: 'accept' | 'refuse') {
    return this.http.post(`${this.baseUrl}/handleJoinRequest`, {requestId: requestId, status: status});
  }

  handleDrivingLessonRequest(requestId: number, status: 'accept' | 'refuse') {
    return this.http.post(`${this.baseUrl}/handleDrivingLessonRequest`, {requestId: requestId, status: status});
  }

  getAllJoinRequestByInstructor(
    id: number
  ): Observable<InstructorJoinRequest[]> {
    return this.http.get<InstructorJoinRequest[]>(`${this.baseUrl}/${id}/joinRequest`);
  }

  getDrivingLessonRequestByInstructor(id: number): Observable<DrivingLessonRequest[]> {
    return this.http.get<DrivingLessonRequest[]>(`${this.baseUrl}/${id}/drivingLessonRequest`);
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

  getInstructorBySearch(schoolId: number, fuelTypeId: number, categoryId: number): Observable<{id: number, name: string}[]> {
    return this.http.get<{id: number, name: string}[]>(`${this.baseUrl}?fuelType=${fuelTypeId}&school=${schoolId}&category=${categoryId}`)
  }

  getInstructorById(id: number):Observable<Instructors> {
    return this.http.get<Instructors>(`${this.baseUrl}/${id}`)
  }

  getStudents(id: number): Observable<{id:number, name: string, imagePath: string, userId: number}[]> {
    return this.http.get<{id:number, name: string, imagePath: string, userId: number}[]>(`${this.baseUrl}/${id}/students?page=0&size=10`)
  }

  kickoutStudent(studentId: number) {
    return this.http.delete(`${this.baseUrl}/kickout?studentId=${studentId}`)
  }
}
