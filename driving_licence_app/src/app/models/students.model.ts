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
  get getStudentSchool(): School {
    return this.studentSchool;
  }
  set setStudentSchool(value: School) {
    this.studentSchool = value;
  }

  get getStudentInstructor(): Instructors {
    return this.studentInstructor;
  }
  set setStudentInstructor(value: Instructors){
    this.studentInstructor = value;
  }

  get getStudentUser(): User {
    return this.studentUser;
  }
  set setStudentUser(value: User) {
    this.studentUser = value;
  }

  get getReviewList(): Review[] {
    return this.reviewList;
  }
  set setReviewList(value: Review[]){
    this.reviewList = value;
  }

  get getRequestList(): Request[] {
    return this.requestList;
  }
  set setRequestList(value: Request[]){
    this.requestList = value;
  }

  get getDrivingLessons(): DrivingLessons[] {
    return this.drivingLessons;
  }
  set setDrivingLessons(value: DrivingLessons[]) {
    this.drivingLessons = value;
  }

}
