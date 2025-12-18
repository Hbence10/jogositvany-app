import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { School } from '../models/school.model';
import { ExamRequest } from '../models/exam-request.model';
import { SchoolJoinRequest } from '../models/school-join-request.model';

@Injectable({
  providedIn: 'root'
})
export class SchoolServiceService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/school';

  constructor() { }

  getSchoolById(id: number): Observable<School> {
    return this.http.get<School>(`${this.baseUrl}/${id}`)
  }

  handleJoinRequest(id: number, status: "accept" | "refuse") {
    return this.http.post("", {})
  }

  getAllJoinRequest(id: number): Observable<SchoolJoinRequest[]> {
    return this.http.get<SchoolJoinRequest[]>("")
  }

  getAllExamRequest(id: number): Observable<ExamRequest[]> {
    return this.http.get<ExamRequest[]>("")
  }
}
