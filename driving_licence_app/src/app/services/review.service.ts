import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Review } from '../models/review.model';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private http = inject(HttpClient)
  baseUrl = "http://localhost:8080/review"

  getReviews(about: "school" | "instructor", aboutId: number): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.baseUrl}?about=${about}&aboutId=${aboutId}`)
  }

  createReview(newReview: {reviewText: string, rating: number, isAnonymous: boolean, studentId: number, instructorId: number | null, schoolId: number | null}): Observable<Review> {
    return this.http.post<Review>(this.baseUrl, newReview)
  }

  updateReview() {

  }

  deleteReview() {

  }
}
