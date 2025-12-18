import { Instructors } from './instructors.model';
import { School } from './school.model';
import { Students } from './students.model';

export class Review {
  constructor(
    public id: number,
    public text: string,
    public createdAt: Date,
    public rating: number,
    public reviewAuthor: Students,
    public aboutInstructor: Instructors,
    public aboutSchool: School
  ) {}

  get getText(): string {
    return this.text;
  }
  set setText(value: string) {
    this.text = value;
  }

  get getCreatedAt(): Date {
    return this.createdAt;
  }
  set setCreatedAt(value: Date) {
    this.createdAt = value;
  }

  get getRating(): number {
    return this.rating;
  }
  set setRating(value: number) {
    this.rating = value;
  }

  get getReviewAuthor(): Students {
    return this.reviewAuthor;
  }
  set setReviewAuthor(value: Students) {
    this.reviewAuthor = value;
  }

  get getAboutInstructor(): Instructors {
    return this.aboutInstructor;
  }
  set setAboutInstructor(value: Instructors) {
    this.aboutInstructor = value;
  }

  get getAboutSchool(): School {
    return this.aboutSchool;
  }
  set setAboutSchool(value: School) {
    this.aboutSchool = value;
  }
}
