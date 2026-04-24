import { Component, inject, input, OnInit, output } from '@angular/core';
import { Instructors } from '../../../models/instructors.model';
import { Review } from '../../../models/review.model';
import { School } from '../../../models/school.model';
import { UsersService } from '../../../services/users.service';
import { ReviewService } from '../../../services/review.service';
import { ReviewWriterComponent } from '../review-writer/review-writer.component';
import { ReviewCardComponent } from '../review-card/review-card.component';
import { AlertServiceService } from '../../../services/alert-service.service';

@Component({
  selector: 'app-review-list',
  imports: [ReviewCardComponent, ReviewWriterComponent],
  templateUrl: './review-list.component.html',
  styleUrl: './review-list.component.css'
})
export class ReviewListComponent implements OnInit {
  userService = inject(UsersService)
  reviewType = input.required<string>()
  aboutObject = input.required<School | Instructors>()

  reviewService = inject(ReviewService)
  reviewList: Review[] = []
  showReviewWriter: boolean = false
  closeList = output()
  showReviewWriterButton: boolean = false
  private alertService = inject(AlertServiceService)

  ngOnInit(): void {
    this.reviewService.getReviews(this.reviewType() == "Oktató" ? "instructor" : "school", this.aboutObject().id).subscribe({
      next: response => this.reviewList = response,
      error: error => {
        this.alertService.setAlert("Hiba történt. Próbáld meg újra később!", "error")
        this.closeList.emit()
      },
      complete: () => {
        this.showReviewWriterButton =
          this.userService.loggedUser()?.role.name == "ROLE_student" &&
          !this.reviewList.map(r => r.reviewAuthor?.id).includes(this.userService.loggedUser()?.studentId)
      }
    })
  }

  createReview(newReview: { reviewText: string, rating: number, isAnonymous: boolean }) {
    this.showReviewWriter = false
    this.reviewService.createReview({
      reviewText: newReview.reviewText,
      rating: newReview.rating,
      isAnonymous: newReview.isAnonymous,
      studentId: this.userService.loggedUser()?.studentId!,
      instructorId: this.reviewType() == "Oktató" ? this.aboutObject().id : 0,
      schoolId: this.reviewType() == "Iskola" ? this.aboutObject().id : 0
    }).subscribe({
      next: response => {
        this.reviewList.push(response)
        this.alertService.setAlert("Sikeres vélemény írás!", "success")
      }, error: () => {
        this.alertService.setAlert("Hiba történt. Próbáld meg újra később!", "error")
      }
    })
  }

  getColumns(): Review[][] {
    const columns: Review[][] = []
    for (let i: number = 0; i < this.reviewList.length; i += 3) {
      const column: Review[] = []
      for (let j: number = i; j < i + 3; j++) {
        if (this.reviewList[j] != undefined) {
          column.push(this.reviewList[j])
        }
      }
      columns.push(column)
    }
    return columns
  }
}
