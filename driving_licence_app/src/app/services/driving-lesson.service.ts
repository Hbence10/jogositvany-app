import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DrivingLessonType } from '../models/driving-lesson-type.model';
import { DrivingLessons } from '../models/driving-lessons.model';

@Injectable({
  providedIn: 'root'
})
export class DrivingLessonService {
  private http = inject(HttpClient)
  baseUrl = "http://localhost:8080/drivingLesson"


  getAllDrivingLessonType(schoolId: number): Observable<DrivingLessonType[]> {
    return this.http.get<DrivingLessonType[]>(`${this.baseUrl}/type`)
  }

  cancelDrivingLesson(id: number) {
    return this.http.delete(`${this.baseUrl}/cancel/${id}`)
  }

  updateDrivingLesson(id: number, body: {startKm: number, endKm: number, location: string, pickUpPlace: string, dropOffPlace: string, lessonHourNumber: number, isPaid: boolean, statusId: number, paymentMethodId: number, typeId: number}) {
    return this.http.put(`${this.baseUrl}/${id}`, body)
  }

  getReservedHourByDate(instructorId: number, wantedDate: string): Observable<{ startTime: Date, endTime: Date, name: string, drivingLessonId: number }[]> {
    return this.http.get<{ startTime: Date, endTime: Date, name: string, drivingLessonId: number }[]>(`${this.baseUrl}/reservedHour?instructorId=${instructorId}&date=${wantedDate}`)
  }

  getDrivingLessonById(id: number): Observable<DrivingLessons> {
    return this.http.get<DrivingLessons>(`${this.baseUrl}/${id}`)
  }
}
