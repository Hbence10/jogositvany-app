import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RequestService {


  private http = inject(HttpClient)
  baseUrl = "http://localhost:8080/users"

  constructor() { }

  sendSchoolJoinRequest(schoolId: number, userId: number, categoryId: number) {
    return this.http.post(`${this.baseUrl}/school`, {
      schoolId: schoolId,
      userId: userId,
      categoryId: categoryId
    })
  }

  sendInstructorJoinRequest(studentId: number, instructorId: number) {
    return this.http.post(`${this.baseUrl}/instructor`, {
      studentId: studentId,
      instructorId: instructorId
    })
  }

  sendDrivingLessonRequest(newRequest: {msg: string, date: string, startTime: string, endTime: string, studentId: number, instructorId: number}) {
    return this.http.post(`${this.baseUrl}/drivingLesson`, newRequest)
  }
}
