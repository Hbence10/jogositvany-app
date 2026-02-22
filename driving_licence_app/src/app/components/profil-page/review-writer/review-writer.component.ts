import { Component, inject, OnInit, output } from '@angular/core';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { UsersService } from '../../../services/users.service';

@Component({
  selector: 'app-review-writer',
  imports: [ReactiveFormsModule],
  templateUrl: './review-writer.component.html',
  styleUrl: './review-writer.component.css'
})
export class ReviewWriterComponent implements OnInit {
  createReview = output<{reviewText: string, rating: number, isAnonymous: boolean}>()
  reviewForm!: FormGroup
  isAnonymous: boolean = false
  userService = inject(UsersService)
  close = output()

  ngOnInit(): void {
    this.isAnonymous = false
    this.reviewForm = new FormGroup({
      reviewText: new FormControl("", [Validators.required]),
      rating: new FormControl("", [Validators.required, Validators.min(0), Validators.max(5)]),
    })
  }

  sendCreate() {
    console.log("sendCrate()")
    this.createReview.emit({
      reviewText: this.reviewForm.controls["reviewText"].value,
      rating: this.reviewForm.controls["rating"].value,
      isAnonymous: this.isAnonymous
    })
  }

  setRating(ratingValue: number) {
    console.log(ratingValue)
    this.reviewForm.controls["rating"].setValue(ratingValue)
  }
}
