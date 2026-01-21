import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DrivingLessons } from '../models/driving-lessons.model';
import { SchoolCategory } from '../models/schoolCategory.model';

@Injectable({
  providedIn: 'root'
})
export class DrivingLessonService {
  private http = inject(HttpClient)
  baseUrl = "http://localhost:8080/drivingLesson"


  getDrivingLicenseCategoriesBySchool(schoolId: number): Observable<SchoolCategory[]> {
    return this.http.get<SchoolCategory[]>(`${this.baseUrl}/categories/school/${schoolId}`)
  }
  cancelDrivingLesson(id: number) {
    return this.http.delete(`${this.baseUrl}/cancel/${id}`)
  }

  updateDrivingLesson(id: number, body: {startKm: number, endKm: number, location: string, pickUpPlace: string, dropOffPlace: string, lessonHourNumber: number, isPaid: boolean, statusId: number, paymentMethodId: number}) {
    return this.http.put(`${this.baseUrl}/${id}`, body)
  }

  getReservedHourByDate(instructorId: number, wantedDate: string): Observable<{ startTime: Date, endTime: Date, name: string, drivingLessonId: number }[]> {
    return this.http.get<{ startTime: Date, endTime: Date, name: string, drivingLessonId: number }[]>(`${this.baseUrl}/reservedHour?instructorId=${instructorId}&date=${wantedDate}`)
  }

  getDrivingLessonById(id: number): Observable<DrivingLessons> {
    return this.http.get<DrivingLessons>(`${this.baseUrl}/${id}`)
  }
}
