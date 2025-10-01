import { DrivingLessons } from "./driving-lessons.model";
import { Review } from "./review.model";
import { School } from "./school.model";
import { Students } from "./students.model";
import { User } from "./user.model";
import { Vehicle } from "./vehicle.model";

export class Instructors {
  constructor(
    private id: number,
    private name: string,
    private promoText: string,
    private instructorUser: User,
    private instructorSchool: School,
    private vehicle: Vehicle,
    private reviewList: Review[],
    private students: Students[],
    private instructorDrivingLessons: DrivingLessons[]
  ){}
}
