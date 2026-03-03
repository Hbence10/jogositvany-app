import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DrivingLessonRequest } from '../models/driving-lesson-request.model';
import { InstructorJoinRequest } from '../models/instructor-join-request.model';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { User } from '../models/user.model';
import { Instructors } from '../models/instructors.model';

@Injectable({
  providedIn: 'root',
})
export class InstructorServiceService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/instructor';

  constructor() { }

  handleJoinRequest(requestId: number, status: 'accept' | 'refuse') {
    return this.http.post(`${this.baseUrl}/handleJoinRequest`, { requestId: requestId, status: status });
  }

  handleDrivingLessonRequest(requestId: number, status: 'accept' | 'refuse') {
    return this.http.post(`${this.baseUrl}/handleDrivingLessonRequest`, { requestId: requestId, status: status });
  }

  getAllJoinRequestByInstructor(id: number, pageNumber: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}/joinRequest?page=${pageNumber}&size=10`, {observe: "response"});
  }

  getDrivingLessonRequestByInstructor(id: number, pageNumber: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}/drivingLessonRequest?page=${pageNumber}&size=10`, {observe: "response"});
  }

  updateInstructor(instructorId: number, promoText: string, vehicleId: number, vehicleName: string, licensePlate: string, fuelTypeId: number, vehicleTypeId: number): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/${instructorId}`, {
      promoText: promoText,
      vehicleId: vehicleId,
      vehicleName: vehicleName,
      licensePlate: licensePlate,
      fuelTypeId: fuelTypeId,
      vehicleTypeId: vehicleTypeId
    })
  }

  getInstructorBySearch(schoolId: number, fuelTypeId: number, categoryId: number): Observable<{ id: number, name: string }[]> {
    return this.http.get<{ id: number, name: string }[]>(`${this.baseUrl}?fuelType=${fuelTypeId}&school=${schoolId}&category=${categoryId}`)
  }

  getInstructorById(id: number): Observable<Instructors> {
    return this.http.get<Instructors>(`${this.baseUrl}/${id}`)
  }

  getStudents(id: number, pageNumber: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}/students?page=${pageNumber}&size=10`, { observe: "response" })
  }

  kickoutStudent(studentId: number) {
    return this.http.delete(`${this.baseUrl}/kickout?studentId=${studentId}`)
  }
}
