import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DrivingLessonType } from '../models/driving-lesson-type.model';

@Injectable({
  providedIn: 'root'
})
export class DrivingLessonService {
  private http = inject(HttpClient)
  baseUrl = "http://localhost:8080/drivingLesson"


  getAllDrivingLessonType(): Observable<DrivingLessonType[]> {
    return this.http.get<DrivingLessonType[]>(`${this.baseUrl}/type`)
  }

  getLessonInformationByStudent() {

  }

  cancelDrivingLesson() {

  }

  getDrivingLessonInformationBetweenTwoDate() {

  }

  updateDrivingLesson() {

  }
}
