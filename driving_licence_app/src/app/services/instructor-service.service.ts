import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DrivingLessonRequest } from '../models/driving-lesson-request.model';
import { InstructorJoinRequest } from '../models/instructor-join-request.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class InstructorServiceService {

  private http = inject(HttpClient);

  constructor() {}

  handleJoinRequest(requestId: number, status: 'accept' | 'refuse') {
    return this.http.post('', {});
  }

  handleDrivingLessonRequest(requestId: number, status: 'accept' | 'refuse') {
    return this.http.post('', {});
  }

  getAllJoinRequestByInstructor(
    id: number
  ): Observable<InstructorJoinRequest[]> {
    return this.http.get<InstructorJoinRequest[]>('');
  }

  getDrivingLessonRequestByInstructor(
    id: number
  ): Observable<DrivingLessonRequest[]> {
    return this.http.get<DrivingLessonRequest[]>('');
  }
}
