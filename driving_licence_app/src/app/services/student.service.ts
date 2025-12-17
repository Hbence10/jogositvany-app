import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private http = inject(HttpClient)
  private baseUrl = "http://localhost:8080/students"

  constructor() { }

  getLessonDetailsForHomePage(studentId: number){
    return this.http.get(`${this.baseUrl}/lessonDetails/${studentId}`)
  }
}
