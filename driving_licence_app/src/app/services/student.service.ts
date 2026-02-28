import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { Students } from '../models/students.model';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private http = inject(HttpClient)
  private baseUrl = "http://localhost:8080/students"

  constructor() { }

  getLessonDetailsForHomePage(studentId: number): Observable<{paidLesson: number, drivenLesson: number, totalLessonNumber: number}>{
    return this.http.get<{paidLesson: number, drivenLesson: number, totalLessonNumber: number}>(`${this.baseUrl}/lessonDetails/${studentId}`)
  }

  getStudentById(id: number): Observable<Students> {
    return this.http.get<Students>(`${this.baseUrl}/${id}`)
  }

  deleteStudent(studentId: number) {
    return this.http.delete(`${this.baseUrl}/${studentId}`)
  }

  getDrivingHistory(id: number):Observable<{date: string, startHour: string, endHour: string, town: string, km: number}[]> {
    return this.http.get<{date: string, startHour: string, endHour: string, town: string, km: number}[]>(`${this.baseUrl}/${id}/history`)
  }
}
