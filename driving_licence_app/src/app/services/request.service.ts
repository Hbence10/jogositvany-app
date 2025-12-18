import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  private http = inject(HttpClient)
  baseUrl = "http://localhost:8080/users"

  constructor() { }

  sendSchoolJoinRequest(schoolId: number, userId: number, requestedRole: "student" | "instructor") {

  }

  sendInstructorJoinRequest(studentId: number, instructorId: number) {

  }
}
