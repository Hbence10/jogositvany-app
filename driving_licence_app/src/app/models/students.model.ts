import { DrivingLessons } from "./driving-lessons.model";
import { Instructors } from "./instructors.model";
import { Review } from "./review.model";
import { School } from "./school.model";
import { User } from "./user.model";

export class Students{
  constructor(
    private id: number,
    private studentSchool: School,
    private studentInstructor: Instructors,
    private studentUser: User,
    private reviewList: Review[],
    private requestList: Request[],
    private drivingLessons: DrivingLessons[]
  ){}
}
